package com.setyongr.pinjamin.presentation.auth.signup

import com.setyongr.pinjamin.base.BaseLeView

interface SignUpView: BaseLeView {
    fun toogleSignUpClick(status: Boolean)
    fun onRegisterSuccess()
    fun showNameError(error: String?)
    fun showUsernameError(error: String?)
    fun showEmailError(error: String?)
    fun showPasswordError(error: String?)
    fun showPasswordRepeatError(error: String?)
}