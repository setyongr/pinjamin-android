package com.setyongr.pinjamin.base

interface BaseLeView: BaseView {
    fun showLoading(status: Boolean)
    fun showError(e: Throwable)
}