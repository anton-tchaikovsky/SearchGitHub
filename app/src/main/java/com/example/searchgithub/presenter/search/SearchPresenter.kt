package com.example.searchgithub.presenter.search

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.repository.GitHubRepositoryCallback
import com.example.searchgithub.repository.IGitHubRepository
import com.example.searchgithub.view.search.ViewSearchContract
import retrofit2.Response

internal class SearchPresenter internal constructor(
    private val repository: IGitHubRepository
) : PresenterSearchContract, GitHubRepositoryCallback {

    internal var viewContract: ViewSearchContract? = null
    internal var searchResults: List<SearchResult> = listOf()
    internal var totalCount = 0

    override fun searchGitHub(searchQuery: String) {
        viewContract?.displayLoading(true)
        repository.searchGithub(searchQuery, this)
    }

    override fun onAttach(view: ViewSearchContract) {
        viewContract = view
        if (searchResults.isNotEmpty() && totalCount>0)
            viewContract?.displaySearchResults(searchResults, totalCount)
    }

    override fun onDetach() {
        viewContract = null
    }

    override fun handleGitHubResponse(response: Response<SearchResponse?>?) {
        viewContract?.displayLoading(false)
        if (response != null && response.isSuccessful) {
            val searchResponse = response.body()
            if (searchResponse?.searchResults != null && searchResponse.totalCount != null) {
                searchResults = searchResponse.searchResults
                totalCount = searchResponse.totalCount
                viewContract?.displaySearchResults(
                    searchResults,
                    totalCount
                )
            } else {
                viewContract?.displayError(SEARCH_RESULT_OR_TOTAL_COUNT_ARE_NULL)
            }
        } else {
            viewContract?.displayError(RESPONSE_IS_NULL_OR_UNSUCCESSFUL)
        }
    }

    override fun handleGitHubError() {
        viewContract?.displayLoading(false)
        viewContract?.displayError()
    }

    companion object{
        const val RESPONSE_IS_NULL_OR_UNSUCCESSFUL = "Response is null or unsuccessful"
        const val SEARCH_RESULT_OR_TOTAL_COUNT_ARE_NULL = "Search results or total count are null"
    }
}
