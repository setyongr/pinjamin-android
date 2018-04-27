package com.setyongr.pinjamin.presentation.main.home

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.Event
import com.setyongr.pinjamin.data.AppState
import com.setyongr.domain.interactor.pinjaman.GetPinjamanListUseCase
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val appState: AppState,
        private val getPinjamanListUseCase: GetPinjamanListUseCase
): BaseRxPresenter<HomeView>() {
    fun clear() {
        appState.pinjamanList.clear()
    }

    fun load() {
        if(appState.pinjamanList.getListData().isEmpty()) {
            getRemote()
        } else {
            appState.pinjamanList.getListData().forEach {
                getView().addPinjaman(it)
            }
        }

        disposables += appState.pinjamanList.getPublishObservable().subscribe {
            when(it) {
                is Event.Added -> {
                    getView().addPinjaman(it.data.second)
                }
            }
        }
    }

    fun getRemote() {
        getView().showLoading(true)
        disposables += getPinjamanListUseCase.execute()
                .subscribeBy(
                        onSuccess = {
                            getView().showLoading(false)
                            it.forEach {
                                appState.pinjamanList.add(it.id, it)
                            }

                        },
                        onError = {
                            it.printStackTrace()
                            getView().showLoading(false)
                            getView().showError(it)
                        }
                )
    }
}