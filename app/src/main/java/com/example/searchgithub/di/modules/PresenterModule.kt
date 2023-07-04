package com.example.searchgithub.di.modules

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
    fun searchPresenter(repository: IGitHubRepository): PresenterSearchContract =
        SearchPresenter(repository)

    @Provides
    fun detailsPresenter(): PresenterDetailsContract =
        DetailsPresenter()
}