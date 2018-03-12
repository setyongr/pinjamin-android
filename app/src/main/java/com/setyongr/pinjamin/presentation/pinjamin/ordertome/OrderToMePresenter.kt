package com.setyongr.pinjamin.presentation.pinjamin.ordertome

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.Event
import com.setyongr.pinjamin.common.applyDefaultSchedulers
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.PinjaminService
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class OrderToMePresenter @Inject constructor(
        private val appState: AppState,
        private val service: PinjaminService,
        private val schedulerProvider: SchedulerProvider
): BaseRxPresenter<OrderToMeView>() {
    fun clear() {
        appState.orderToMe.clear()
    }

    fun load() {
        if (appState.orderToMe.getListData().isEmpty()) {
            getRemote()
        } else {
            appState.orderToMe.getListData().forEach {
                getView().addOrder(it)
            }
        }

        disposables += appState.orderToMe.getPublishObservable().subscribe {
            when (it) {
                is Event.Added -> {
                    getView().addOrder(it.data.second)
                }
            }
        }
    }

    fun getRemote() {
        getView().showLoading(true)
        disposables += service.getOrderToMe()
                .applyDefaultSchedulers(schedulerProvider)
                .subscribeBy(
                        onNext = {
                            getView().showLoading(false)
                            it.forEach {
                                appState.orderToMe.add(it.id, it)
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