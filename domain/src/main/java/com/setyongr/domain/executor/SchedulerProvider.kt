package com.setyongr.domain.executor

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui() : Scheduler
    fun io() : Scheduler
    fun computation() : Scheduler
}