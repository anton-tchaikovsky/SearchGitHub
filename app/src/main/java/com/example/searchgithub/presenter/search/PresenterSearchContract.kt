package com.example.searchgithub.presenter.search

import com.example.searchgithub.presenter.PresenterContract
import com.example.searchgithub.view.search.ViewSearchContract

interface PresenterSearchContract : PresenterContract<ViewSearchContract> {
    fun searchGitHub(searchQuery: String)
}
