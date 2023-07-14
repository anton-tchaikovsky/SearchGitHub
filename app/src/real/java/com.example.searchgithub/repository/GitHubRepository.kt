package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import javax.inject.Inject

class GitHubRepository @Inject constructor (private val gitHubApi: GitHubApi): IGitHubRepository {

    override suspend fun searchGithub(query: String): SearchResponse =
        gitHubApi.searchGithubAsync(query).await()

}
