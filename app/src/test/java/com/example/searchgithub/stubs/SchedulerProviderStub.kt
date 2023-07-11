package com.example.searchgithub.stubs

import com.example.githubusers.scheduler_provider.ISchedulerProvider
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

class SchedulerProviderStub: ISchedulerProvider {
    override fun mainScheduler(): Scheduler = Schedulers.trampoline()

    override fun ioScheduler(): Scheduler = Schedulers.trampoline()
}