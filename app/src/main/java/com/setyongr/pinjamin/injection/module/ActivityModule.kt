package com.setyongr.pinjamin.injection.module

import android.app.Activity
import android.content.Context
import com.setyongr.pinjamin.common.rx.AppSchedulerProvider
import com.setyongr.pinjamin.common.rx.SchedulerProvider
import com.setyongr.pinjamin.injection.ActivityContext
import com.setyongr.pinjamin.injection.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(val activity: Activity) {

    @Provides
    @PerActivity
    fun provideActivity(): Activity = activity

    @Provides
    @ActivityContext
    fun provideActivityContext(): Context = activity

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}