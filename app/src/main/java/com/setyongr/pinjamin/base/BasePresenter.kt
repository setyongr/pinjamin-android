package com.setyongr.pinjamin.base

import java.lang.ref.WeakReference

open class BasePresenter<T: BaseView>: Presenter<T> {
    private var viewRef: WeakReference<T>? = null

    override fun attachView(view: T) {
        viewRef = WeakReference(view)
    }

    override fun detachView() {
        viewRef?.clear()
        viewRef = null
    }

    fun isViewAttached() = viewRef != null && viewRef?.get() != null

    fun getView() = viewRef?.get() ?: throw MvpViewNotAttachedException()

    class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before" +
            " requesting data to the Presenter")

}