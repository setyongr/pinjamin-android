package com.setyongr.pinjamin.presentation.auth.signin

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.data.AppState
import com.setyongr.domain.interactor.user.LoginUseCase
import com.setyongr.domain.model.Login
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import retrofit2.HttpException
import javax.inject.Inject

class SignInPresenter @Inject constructor(
        private val loginUseCase: LoginUseCase,
        private val appState: AppState
): BaseRxPresenter<SignInView>() {
    fun doLogin(username: String, password: String) {
        getView().showLoading(true)

        disposables += loginUseCase.execute(Login(username, password))
                .subscribeBy(
                        onSuccess = {
                            appState.setLoggedIn(true)
                            appState.saveUser(it)
                            getView().showLoading(false)
                            getView().onLoginSuccess()
                        },
                        onError = {
                            it.printStackTrace()
                            getView().showLoading(false)
                            if (it is HttpException) {
                                if (it.code() == 400) {
                                    getView().showToast("Error! Please check your credentials")
                                }
                            }
                            getView().showToast("Failed to Sign In")
                        }
                )

    }
}