package com.example.searchgithub.view.search

import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.view.ViewContract

interface ViewSearchContract : ViewContract {
    fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    )
    fun displayError(error: String)
    fun displayLoading(show: Boolean)
}
