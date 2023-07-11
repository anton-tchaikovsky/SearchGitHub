package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import io.reactivex.rxjava3.core.Single

interface IGitHubRepository {
    fun searchGithub(
        query: String
    ): Single<SearchResponse>
}