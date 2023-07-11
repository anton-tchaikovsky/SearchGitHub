package com.example.searchgithub.di.modules

import com.example.githubusers.scheduler_provider.ISchedulerProvider
import com.example.searchgithub.scheduler_provider.SchedulerProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface SchedulerProviderModule {

    @Singleton
    @Binds
    fun  schedulerProvider(schedulerProvider: SchedulerProvider): ISchedulerProvider

}