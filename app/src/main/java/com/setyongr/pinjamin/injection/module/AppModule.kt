package com.setyongr.pinjamin.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.AppTokenProvider
import com.setyongr.pinjamin.data.TokenProvider
import com.setyongr.pinjamin.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: Application) {

    @Provides
    fun provideApplication(): Application = app

    @Provides
    @ApplicationContext
    fun provideContext(): Context = app

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideTokenProvider(sharedPreferences: SharedPreferences): TokenProvider = AppTokenProvider(sharedPreferences)

    @Provides
    @Singleton
    fun provideAppState(sharedPreferences: SharedPreferences, tokenProvider: TokenProvider) = AppState(sharedPreferences, tokenProvider)
}