package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import retrofit2.Response

interface GitHubRepositoryCallback {
    fun handleGitHubResponse(response: Response<SearchResponse?>?)
    fun handleGitHubError()
}