package com.setyongr.pinjamin.presentation.signin

import com.setyongr.pinjamin.base.BaseLeView

interface SignInView: BaseLeView {
    fun showToast(message: String)
    fun onLoginSuccess()
}