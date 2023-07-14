package com.example.searchgithub.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.searchgithub.model.AppState
import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.IGitHubRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import java.io.IOException

class SearchViewModel(
    private val repository: IGitHubRepository
) : ViewModel() {

    private val liveData: MutableLiveData<AppState> = MutableLiveData()

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main
                + SupervisorJob()
                + CoroutineExceptionHandler { _, throwable ->
            handleGitHubError(throwable)
        })

    override fun onCleared() {
        super.onCleared()
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    fun getLiveData(): LiveData<AppState> = liveData

    fun searchGitHub(searchQuery: String) {
        liveData.value = AppState.Loading
        viewModelCoroutineScope.launch {
            handleGitHubResponse(repository.searchGithub(searchQuery))
        }
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