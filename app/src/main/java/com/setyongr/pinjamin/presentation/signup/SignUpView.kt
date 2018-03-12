package com.setyongr.pinjamin.presentation.signup

import com.setyongr.pinjamin.base.BaseLeView

interface SignUpView: BaseLeView {
    fun toogleSignUpClick(status: Boolean)
    fun onRegisterSuccess()
}