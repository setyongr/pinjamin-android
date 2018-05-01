package com.setyongr.pinjamin.injection.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.setyongr.data.*
import com.setyongr.data.remote.PinjaminService
import com.setyongr.data.remote.mapper.LoginRemoteMapper
import com.setyongr.data.remote.mapper.OrderRemoteMapper
import com.setyongr.data.remote.mapper.PinjamanRemoteMapper
import com.setyongr.data.remote.mapper.UserRemoteMapper
import com.setyongr.domain.executor.SchedulerProvider
import com.setyongr.pinjamin.data.AppState
import com.setyongr.pinjamin.data.AppTokenProvider
import com.setyongr.domain.interactor.TokenProvider
import com.setyongr.domain.repository.*
import com.setyongr.pinjamin.common.rx.AppSchedulerProvider
import com.setyongr.pinjamin.injection.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    fun provideSharedPreferences(context: Application) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    @Singleton
    fun provideTokenProvider(sharedPreferences: SharedPreferences): TokenProvider = AppTokenProvider(sharedPreferences)

    @Provides
    @Singleton
    fun provideAppState(sharedPreferences: SharedPreferences, tokenProvider: TokenProvider) = AppState(sharedPreferences, tokenProvider)

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Provides
    @Singleton
    fun provideUserRepository(service: PinjaminService, userRemoteMapper: UserRemoteMapper, loginRemoteMapper: LoginRemoteMapper)
            : UserRepository = UserDataRepository(service, userRemoteMapper, loginRemoteMapper)

    @Provides
    @Singleton
    fun providePinjaminRepository(service: PinjaminService, pinjamanRemoteMapper: PinjamanRemoteMapper): PinjamanRepository =
            PinjamanDataRepository(service, pinjamanRemoteMapper)

    @Provides
    @Singleton
    fun provodePartnerPinjamanRepository(service: PinjaminService, pinjamanRemoteMapper: PinjamanRemoteMapper): PartnerPinjamanRepository =
            PartnerPinjamanDataRepository(service, pinjamanRemoteMapper)

    @Provides
    @Singleton
    fun provideOrderRepository(service: PinjaminService, orderRemoteMapper: OrderRemoteMapper) : OrderRepository =
            OrderDataRepository(service, orderRemoteMapper)

    @Provides
    @Singleton
    fun providePartnerOrderRepository(service: PinjaminService, orderRemoteMapper: OrderRemoteMapper) : PartnerOrderRepository =
            PartnerOrderDataRepository(service, orderRemoteMapper)
}