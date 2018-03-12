package com.setyongr.pinjamin.presentation.signin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.common.hideKeyboard
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_signin.*
import javax.inject.Inject

class SignInActivity: BaseInjectedActivity(), SignInView {

    @Inject lateinit var mPresenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        mPresenter.attachView(this)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Sign In"

        signin_button.setOnClickListener {
            mPresenter.doLogin(username_input.editText?.text.toString(), password_input.editText?.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun onLoginSuccess() {
        val intent = Intent(this, MainActivity::class.java)
        move(intent, finishActivity = true, newTask = true)
    }

    override fun showLoading(status: Boolean) {
        this.hideKeyboard()
        progressBar.visibility = if (status) View.VISIBLE else View.INVISIBLE
    }

    override fun showError(e: Throwable) {
        Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
    }

}