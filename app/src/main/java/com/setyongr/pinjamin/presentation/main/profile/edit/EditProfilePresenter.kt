package com.setyongr.pinjamin.presentation.main.profile.edit

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import com.setyongr.pinjamin.data.models.RequestModel
import com.setyongr.pinjamin.data.models.ResponseModel
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class EditProfilePresenter @Inject constructor(
    private val service: PinjaminService,
    private val schedulerProvider: SchedulerProvider,
    private val appState: AppState
): BaseRxPresenter<EditProfileView>() {
    private val editObserver = object : Observer<ResponseModel.User> {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
            disposables += d
        }

        override fun onNext(t: ResponseModel.User) {
            getView().showLoading(false)
            appState.saveUser(t)
            getView().onSuccess()
        }

        override fun onError(e: Throwable) {
            getView().showLoading(false)
            getView().showError(e)
        }

    }
    fun saveHP(hp: String) {
        getView().showLoading(true)
        service.updateMe(RequestModel.User(noHp = hp))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeWith(editObserver)
    }

    fun saveEmail(email: String) {
        getView().showLoading(true)
        service.updateMe(RequestModel.User(email = email))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeWith(editObserver)
    }

    fun saveName(name: String) {
        getView().showLoading(true)
        service.updateMe(RequestModel.User(name = name))
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeWith(editObserver)
    }
}