package com.setyongr.domain

import com.setyongr.domain.executor.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Single

fun <T> Single<T>.applyDefaultSchedulers(provider: SchedulerProvider) =
        this.subscribeOn(provider.io()).observeOn(provider.ui())

fun Completable.applyDefaultSchedulers(provider: SchedulerProvider) =
        this.subscribeOn(provider.io()).observeOn(provider.ui())