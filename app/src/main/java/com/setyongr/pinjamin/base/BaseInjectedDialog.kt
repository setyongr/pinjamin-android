package com.setyongr.pinjamin.base

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.setyongr.pinjamin.App
import com.setyongr.pinjamin.injection.component.ActivityComponent
import com.setyongr.pinjamin.injection.module.ActivityModule

abstract class BaseInjectedDialog : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        val activityComponent = App.get(activity!!)
                .appComponent
                .activityComponent()
                .activityModule(ActivityModule(activity!!))
                .build()

        injectModule(activityComponent)
    }

    abstract fun injectModule(activityComponent: ActivityComponent)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(getLayout(), container, false)

    abstract fun getLayout(): Int

    override fun onResume() {
        super.onResume()
        val params = dialog.window!!.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}