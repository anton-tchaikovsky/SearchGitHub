package com.example.searchgithub.di.modules

import com.example.searchgithub.repository.GitHubRepository
import com.example.searchgithub.repository.IGitHubRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton


@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun repository(repository: GitHubRepository): IGitHubRepository

}