package com.setyongr.pinjamin.presentation.signup

import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.jakewharton.rxbinding2.widget.textChanges
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.common.hideKeyboard
import com.setyongr.pinjamin.injection.component.ActivityComponent
import kotlinx.android.synthetic.main.activity_signup.*
import javax.inject.Inject

class SignUpActivity: BaseInjectedActivity(), SignUpView {
    @Inject lateinit var mPresenter: SignUpPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mPresenter.attachView(this)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Sign Up"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        mPresenter.setUp(
                nameObs = name_input.editText?.textChanges(),
                emailObs = email_input.editText?.textChanges(),
                usernameObs = username_input.editText?.textChanges(),
                passwordObs = password_input.editText?.textChanges(),
                passwordRepeatObs = password_repeat_input.editText?.textChanges()
        )

        signup_button.setOnClickListener {
            mPresenter.signUp()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun showLoading(status: Boolean) {
        this.hideKeyboard()
        progressBar.visibility = if (status) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(e: Throwable) {
        Toast.makeText(this, "Failed to register", Toast.LENGTH_LONG).show()
    }

    override fun onRegisterSuccess() {
        val dialog = AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Register success")
                .setPositiveButton("OK") {
                    dialogInterface: DialogInterface, _: Int ->
                    dialogInterface.dismiss()

                    finish()
                }
                .create()

        dialog.show()
    }

    override fun toogleSignUpClick(status: Boolean) {
        signup_button.isEnabled = status
    }

    override fun showNameError(error: String?) {
        name_input.isErrorEnabled = error != null
        name_input.error = error
    }

    override fun showUsernameError(error: String?) {
        username_input.isErrorEnabled = error != null
        username_input.error = error
    }

    override fun showEmailError(error: String?) {
        email_input.isErrorEnabled = error != null
        email_input.error = error
    }

    override fun showPasswordError(error: String?) {
        password_input.isErrorEnabled = error != null
        password_input.error = error
    }

    override fun showPasswordRepeatError(error: String?) {
        password_repeat_input.isErrorEnabled = error != null
        password_repeat_input.error = error
    }

}