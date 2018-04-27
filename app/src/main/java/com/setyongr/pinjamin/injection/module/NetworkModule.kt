package com.setyongr.pinjamin.injection.module

import com.setyongr.data.remote.PinjaminService
import com.setyongr.domain.interactor.TokenProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule{
    @Provides
    @Singleton
    fun provideMovieService(tokenProvider: TokenProvider) : PinjaminService = PinjaminService.create(tokenProvider)
}