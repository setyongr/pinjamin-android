package com.setyongr.pinjamin.presentation.main.order

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.Event
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class OrderPresenter @Inject constructor(
        private val appState: AppState,
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider
): BaseRxPresenter<OrderView>() {
    fun clear() {
        appState.myOrder.clear()
    }

    fun load() {
        if(appState.myOrder.getListData().isEmpty()) {
            getRemote()
        } else {
            appState.myOrder.getListData().forEach {
                getView().addOrder(it)
            }
        }

        disposables += appState.myOrder.getPublishObservable().subscribe {
            when(it) {
                is Event.Added -> {
                    getView().addOrder(it.data.second)
                }
            }
        }
    }

    fun getRemote() {
        getView().showLoading(true)
        disposables += service.getOrder()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            getView().showLoading(false)
                            it.forEach {
                                appState.myOrder.add(it.id, it)
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