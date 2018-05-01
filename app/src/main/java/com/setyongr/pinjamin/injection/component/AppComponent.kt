package com.setyongr.pinjamin.injection.component

import android.app.Application
import com.setyongr.pinjamin.App
import com.setyongr.pinjamin.injection.module.ActivityBindingModule
import com.setyongr.pinjamin.injection.module.AppModule
import com.setyongr.pinjamin.injection.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class,
    NetworkModule::class
])

interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }

    fun inject(app: App)
}