package com.setyongr.pinjamin.base

interface Presenter<in V: BaseView> {
    fun attachView(view: V)
    fun detachView()
}