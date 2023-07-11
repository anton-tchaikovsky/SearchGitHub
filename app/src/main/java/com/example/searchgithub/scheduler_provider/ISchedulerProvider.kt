package com.example.githubusers.scheduler_provider

import io.reactivex.rxjava3.core.Scheduler

interface ISchedulerProvider {

    fun mainScheduler(): Scheduler

    fun ioScheduler(): Scheduler

}