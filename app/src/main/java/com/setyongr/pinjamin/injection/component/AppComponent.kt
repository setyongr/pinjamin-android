package com.setyongr.pinjamin.injection.component

import com.setyongr.pinjamin.injection.module.NetworkModule
import com.setyongr.pinjamin.injection.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (NetworkModule::class)])
interface AppComponent {
    fun activityComponent(): ActivityComponent.Builder
}