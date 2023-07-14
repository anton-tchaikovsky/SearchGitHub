package com.example.searchgithub.scheduler_provider

import com.example.githubusers.scheduler_provider.ISchedulerProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SchedulerProvider @Inject constructor(): ISchedulerProvider {
    override fun mainScheduler(): Scheduler = AndroidSchedulers.mainThread()

    override fun ioScheduler(): Scheduler = Schedulers.io()
}