package com.example.searchgithub.di.modules

import com.example.githubusers.scheduler_provider.ISchedulerProvider
import com.example.searchgithub.presenter.details.DetailsPresenter
import com.example.searchgithub.presenter.details.PresenterDetailsContract
import com.example.searchgithub.presenter.search.PresenterSearchContract
import com.example.searchgithub.presenter.search.SearchPresenter
import com.example.searchgithub.repository.IGitHubRepository
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun searchPresenter(repository: IGitHubRepository, schedulerProvider: ISchedulerProvider): PresenterSearchContract =
        SearchPresenter(repository, schedulerProvider)

    @Provides
    fun detailsPresenter(): PresenterDetailsContract =
        DetailsPresenter()
}