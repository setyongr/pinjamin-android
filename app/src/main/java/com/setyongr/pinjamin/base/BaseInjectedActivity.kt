package com.setyongr.pinjamin.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.setyongr.pinjamin.App
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.injection.module.ActivityModule

abstract class BaseInjectedActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityComponent = App.get(this)
                .appComponent
                .activityComponent()
                .activityModule(ActivityModule(this))
                .build()

        injectModule(activityComponent)
    }

    abstract fun injectModule(activityComponent: ActivityComponent)
}