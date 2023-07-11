package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class GitHubRepository @Inject constructor (private val gitHubApi: GitHubApi): IGitHubRepository {

    override fun searchGithub(query: String): Single<SearchResponse> =
        gitHubApi.searchGithub(query)

}
