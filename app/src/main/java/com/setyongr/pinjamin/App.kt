package com.setyongr.pinjamin

import android.app.Application
import android.content.Context
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.setyongr.pinjamin.injection.component.AppComponent
import com.setyongr.pinjamin.injection.component.DaggerAppComponent
import com.setyongr.pinjamin.injection.module.AppModule
import timber.log.Timber

class App: Application() {
    companion object {
        fun get(context: Context): App = context.applicationContext as App
    }

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder().appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        RxPaparazzo.register(this).withFileProviderPath("Pinjamin")
        Timber.plant(Timber.DebugTree())
    }
}