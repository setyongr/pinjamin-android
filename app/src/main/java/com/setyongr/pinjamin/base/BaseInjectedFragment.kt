package com.setyongr.pinjamin.base

import android.os.Bundle
import com.setyongr.pinjamin.App
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.injection.module.ActivityModule

abstract class BaseInjectedFragment: BaseFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityComponent = App.get(activity!!)
                .appComponent
                .activityComponent()
                .activityModule(ActivityModule(activity!!))
                .build()

        injectModule(activityComponent)
    }

    abstract fun injectModule(activityComponent: ActivityComponent)
}