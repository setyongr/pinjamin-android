package com.setyongr.pinjamin.presentation.signup

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.isEmailText
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.RequestModel
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class SignUpPresenter @Inject constructor(
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider
): BaseRxPresenter<SignUpView>() {
    var email = ""
    var username = ""
    var password = ""

    fun setUp(
            emailObs: Observable<CharSequence>?,
            usernameObs: Observable<CharSequence>?,
            passwordObs: Observable<CharSequence>?,
            passwordRepeatObs: Observable<CharSequence>?
    ) {
        if (
                emailObs != null &&
                usernameObs != null &&
                passwordObs != null &&
                passwordRepeatObs != null
        ) {
            val validEmail = emailObs.map {
                email = it.toString()
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
                val minLength = password.length >= 6

                isNotBlank && isSame && minLength
            })

            val signUpObs = Observables.combineLatest(validEmail, validUsername, validPassword, {
                email, username, password -> email && username && password
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
        disposables += service.register(RequestModel.Register(username = username, email = email, password = password))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            getView().showLoading(false)
                            getView().onRegisterSuccess()
                        },
                        onError = {
                            Timber.e(it)
                            getView().showLoading(false)
                            getView().showError(it)
                        }
                )
    }
}