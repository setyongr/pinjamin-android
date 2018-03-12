package com.setyongr.pinjamin.presentation.signin

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.TokenProvider
import com.setyongr.pinjamin.data.models.RequestModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class SignInPresenter @Inject constructor(
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider,
        private val tokenProvider: TokenProvider,
        private val appState: AppState
): BaseRxPresenter<SignInView>() {
    fun doLogin(username: String, password: String) {
        getView().showLoading(true)
        disposables += service.login(RequestModel.Login(username, password))
                .applyDefaultSchedulers(schedulerProvider)
                .flatMap {
                    tokenProvider.setToken(it.token)
                    appState.setLoggedIn(true)
                    service.me().applyDefaultSchedulers(schedulerProvider)
                }
                .subscribeBy(
                        onNext = {
                            appState.saveUser(it)
                            getView().showLoading(false)
                            getView().onLoginSuccess()
                        },
                        onError = {
                            it.printStackTrace()
                            getView().showLoading(false)
                            getView().showToast("Error")
                        }
                )

    }
}