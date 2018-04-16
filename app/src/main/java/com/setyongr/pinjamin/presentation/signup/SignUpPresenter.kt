package com.setyongr.pinjamin.presentation.signup

import com.google.gson.Gson
import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.isEmailText
import com.setyongr.pinjamin.common.parse
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.RequestModel
import com.setyongr.pinjamin.data.models.ResponseModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider
): BaseRxPresenter<SignUpView>() {
    var name = ""
    var email = ""
    var username = ""
    var password = ""

    fun setUp(
            nameObs: Observable<CharSequence>?,
            emailObs: Observable<CharSequence>?,
            usernameObs: Observable<CharSequence>?,
            passwordObs: Observable<CharSequence>?,
            passwordRepeatObs: Observable<CharSequence>?
    ) {
        if (
                nameObs != null &&
                emailObs != null &&
                usernameObs != null &&
                passwordObs != null &&
                passwordRepeatObs != null
        ) {
            val validName = nameObs.map {
                name = it.toString()
                getView().showNameError(if (it.isBlank()) "Name can't be blank" else null)
                it.isNotBlank()
            }

            val validEmail = emailObs.map {
                email = it.toString()
                getView().showEmailError(if (it.isNotBlank() && !it.isEmailText()) "Email not valid" else null)
                it.isEmailText() && it.isNotBlank()
            }

            val validUsername = usernameObs.map {
                username = it.toString()
                it.isNotBlank()
            }

            val validPassword = Observables.combineLatest(passwordObs, passwordRepeatObs, {
                password, password_repeat ->
                this.password = password.toString()
                val isNotBlank = password.isNotBlank()
                val isSame = password_repeat.toString() == password.toString()
                val minLength = password.length >= 8

                getView().showPasswordRepeatError(if (isNotBlank && !isSame) "Password not match" else null)
                getView().showPasswordError(if (isNotBlank && !minLength) "Minumum 8 character" else null)

                isNotBlank && isSame && minLength
            })

            val signUpObs = Observables.combineLatest(validName, validEmail, validUsername, validPassword, {
                name, email, username, password -> name && email && username && password
            })

            disposables += signUpObs.subscribe {
                getView().toogleSignUpClick(it)
            }
        } else {
            Timber.d("Null on setup")
        }
    }


    fun signUp() {
        getView().showLoading(true)
        disposables += service.register(RequestModel.Register(name = name, username = username, email = email, password = password))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            getView().showLoading(false)
                            getView().onRegisterSuccess()
                        },
                        onError = {
                            getView().showLoading(false)
                            getView().showError(it)

                            if (it is HttpException && it.code() == 400) {
                                val error = it.response().errorBody()?.parse<ResponseModel.SignUpError>()
                                getView().showNameError(error?.name?.firstOrNull())
                                getView().showUsernameError(error?.username?.firstOrNull())
                                getView().showEmailError(error?.email?.firstOrNull())
                                getView().showPasswordError(error?.password?.firstOrNull())
                            } else {
                                Timber.e(it)
                            }
                        }
                )
    }
}