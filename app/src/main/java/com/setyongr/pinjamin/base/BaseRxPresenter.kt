package com.setyongr.pinjamin.base

import io.reactivex.disposables.CompositeDisposable

open class BaseRxPresenter<T: BaseView>: BasePresenter<T>() {
    protected var disposables = CompositeDisposable()

    override fun attachView(view: T) {
        super.attachView(view)
    }
    override fun detachView() {
        super.detachView()
        disposables.dispose()
    }
}