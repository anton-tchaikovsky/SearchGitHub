package com.example.searchgithub.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubusers.scheduler_provider.ISchedulerProvider
import com.example.searchgithub.repository.IGitHubRepository
import javax.inject.Inject

class SearchViewModelFactory @Inject constructor(
    private val repository: IGitHubRepository,
    private val schedulerProvider: ISchedulerProvider
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(repository, schedulerProvider) as T
    }
}