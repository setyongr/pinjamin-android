package com.setyongr.pinjamin.presentation.auth.portal

import android.content.Intent
import android.os.Bundle
import com.setyongr.pinjamin.R
import com.setyongr.pinjamin.base.BaseInjectedActivity
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.presentation.user.main.MainActivity
import com.setyongr.pinjamin.presentation.auth.signin.SignInActivity
import com.setyongr.pinjamin.presentation.auth.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_portal.*
import javax.inject.Inject

class PortalActivity: BaseInjectedActivity(), PortalView {

    @Inject
    lateinit var mPresenter: PortalPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_portal)

        mPresenter.attachView(this)

        mPresenter.checkLoggedIn()

        sign_in_link.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        sign_up_email.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
    }

    override fun injectModule(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onLoggedIn() {
        move(Intent(this, MainActivity::class.java), finishActivity = true, newTask = true)
    }
}