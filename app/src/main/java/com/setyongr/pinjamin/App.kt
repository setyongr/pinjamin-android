package com.setyongr.pinjamin

import android.app.Application
import android.content.Context
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import timber.log.Timber
import javax.inject.Inject
import android.app.Activity
import com.setyongr.pinjamin.injection.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector


class App: Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector : DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        RxPaparazzo.register(this).withFileProviderPath("Pinjamin")
        Timber.plant(Timber.DebugTree())

        DaggerAppComponent.builder().application(this).build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

}