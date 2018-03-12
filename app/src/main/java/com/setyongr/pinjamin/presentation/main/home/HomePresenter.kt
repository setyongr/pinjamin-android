package com.setyongr.pinjamin.presentation.main.home

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.Event
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomePresenter @Inject constructor(
        private val appState: AppState,
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider
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
        disposables += service.getPinjaman()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
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