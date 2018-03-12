package com.setyongr.pinjamin.common.rx

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui() : Scheduler
    fun io() : Scheduler
    fun computation() : Scheduler
}