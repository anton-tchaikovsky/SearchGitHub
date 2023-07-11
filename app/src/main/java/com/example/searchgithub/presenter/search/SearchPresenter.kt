package com.example.searchgithub.presenter.search

import com.example.githubusers.scheduler_provider.ISchedulerProvider
import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.repository.IGitHubRepository
import com.example.searchgithub.view.search.ViewSearchContract
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import javax.inject.Inject

class SearchPresenter @Inject internal constructor(
    private val repository: IGitHubRepository, private val schedulerProvider: ISchedulerProvider
) : PresenterSearchContract {

    internal var viewContract: ViewSearchContract? = null
    internal var searchResults: List<SearchResult> = listOf()
    internal var totalCount = 0
    private val compositeDisposable = CompositeDisposable()

    override fun searchGitHub(searchQuery: String) {
        compositeDisposable.add(repository.searchGithub(searchQuery)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .doOnSubscribe {
                viewContract?.displayLoading(true)
            }
            .doOnTerminate {
                viewContract?.displayLoading(false)
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

    override fun onAttach(view: ViewSearchContract) {
        viewContract = view
        if (searchResults.isNotEmpty() && totalCount > 0)
            viewContract?.displaySearchResults(searchResults, totalCount)
    }

    override fun onDetach() {
        viewContract = null
    }

    private fun handleGitHubResponse(searchResponse: SearchResponse) {
        with(searchResponse){
            if (searchResults != null && totalCount != null) {
                this@SearchPresenter.searchResults = searchResults
                this@SearchPresenter.totalCount = totalCount
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError(SEARCH_RESULT_OR_TOTAL_COUNT_ARE_NULL)
            }
        }

    }

    private fun handleGitHubError(error: Throwable) {
        viewContract?.displayError(error.message?: RESPONSE_UNSUCCESSFUL)
    }



    companion object {
        const val RESPONSE_UNSUCCESSFUL = "Response is null or unsuccessful"
        const val SEARCH_RESULT_OR_TOTAL_COUNT_ARE_NULL = "Search results or total count are null"
    }
}
