package com.example.searchgithub.di.modules

import com.example.searchgithub.presenter.details.DetailsPresenter
import com.example.searchgithub.presenter.details.PresenterDetailsContract
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun detailsPresenter(): PresenterDetailsContract =
        DetailsPresenter()
}