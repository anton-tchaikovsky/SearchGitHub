package com.example.searchgithub.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusers.scheduler_provider.ISchedulerProvider
import com.example.searchgithub.model.AppState
import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.IGitHubRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.io.IOException

class SearchViewModel(
    private val repository: IGitHubRepository,
    private val schedulerProvider: ISchedulerProvider
) : ViewModel() {

    private val liveData: MutableLiveData<AppState> = MutableLiveData()
    private val compositeDisposable = CompositeDisposable()

    fun getLiveData(): LiveData<AppState> = liveData

    fun searchGitHub(searchQuery: String) {
        compositeDisposable.add(repository.searchGithub(searchQuery)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .doOnSubscribe {
                liveData.value = AppState.Loading
            }
            .subscribeBy(
                onSuccess = {
                    handleGitHubResponse(it)
                },
                onError = {
                    handleGitHubError(it)
                }
            )
        )
    }

    private fun handleGitHubResponse(searchResponse: SearchResponse) {
        searchResponse.let {
            if (it.searchResults != null && it.totalCount != null) {
                liveData.value = AppState.Success(it)
            } else {
                liveData.value = AppState.Error(IOException(SEARCH_RESULT_OR_TOTAL_COUNT_ARE_NULL))
            }
        }
    }

    private fun handleGitHubError(error: Throwable) {
        liveData.value = AppState.Error(error)
    }

    companion object {
        const val SEARCH_RESULT_OR_TOTAL_COUNT_ARE_NULL = "Search results or total count are null"
    }

}