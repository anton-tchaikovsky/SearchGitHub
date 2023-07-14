package com.example.searchgithub.di.modules

import com.example.searchgithub.repository.IGitHubRepository
import com.example.searchgithub.view_model.SearchViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun searchViewModelFactory(
        repository: IGitHubRepository
    ): SearchViewModelFactory = SearchViewModelFactory(repository)

}