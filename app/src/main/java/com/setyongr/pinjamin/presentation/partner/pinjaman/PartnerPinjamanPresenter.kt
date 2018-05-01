package com.setyongr.pinjamin.presentation.partner.pinjaman

import com.setyongr.pinjamin.base.BaseRxPresenter
import com.setyongr.pinjamin.common.Event
import com.setyongr.pinjamin.data.AppState
import com.setyongr.domain.interactor.pinjaman.partner.GetPartnerPinjamanListUseCase
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PartnerPinjamanPresenter @Inject constructor(
        private val appState: AppState,
        private val partnerPinjamanListUseCase: GetPartnerPinjamanListUseCase
): BaseRxPresenter<PartnerPinjamanView>() {
    fun clear() {
        appState.myPinjaman.clear()
    }

    fun load() {
        if (appState.myPinjaman.getListData().isEmpty()) {
            getRemote()
        } else {
            appState.myPinjaman.getListData().forEach {
                getView().addPinjaman(it)
            }
        }

        disposables += appState.myPinjaman.getPublishObservable().subscribe {
            when (it) {
                is Event.Added -> {
                    getView().addPinjaman(it.data.second)
                }
            }
        }
    }

    fun getRemote() {
        getView().showLoading(true)
        disposables += partnerPinjamanListUseCase.execute()
                .subscribeBy(
                        onSuccess = {
                            getView().showLoading(false)
                            it.forEach {
                                appState.myPinjaman.add(it.id, it)
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