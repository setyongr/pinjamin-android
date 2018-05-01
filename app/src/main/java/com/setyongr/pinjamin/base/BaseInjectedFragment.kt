package com.setyongr.pinjamin.base

import android.content.Context
import dagger.android.support.AndroidSupportInjection

abstract class BaseInjectedFragment: BaseFragment() {
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}