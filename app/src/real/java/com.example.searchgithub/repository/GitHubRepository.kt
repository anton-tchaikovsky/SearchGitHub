package com.example.searchgithub.repository

import com.example.searchgithub.App
import com.example.searchgithub.model.SearchResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class GitHubRepository @Inject constructor (private val gitHubApi: GitHubApi): IGitHubRepository {

    init {
        App.instance.appComponent.inject(this)
    }

    override fun searchGithub(
        query: String,
        callback: GitHubRepositoryCallback
    ) {
        val call = gitHubApi.searchGithub(query)
        call?.enqueue(object : Callback<SearchResponse?> {

            override fun onResponse(
                call: Call<SearchResponse?>,
                response: Response<SearchResponse?>
            ) {
                callback.handleGitHubResponse(response)
            }

            override fun onFailure(
                call: Call<SearchResponse?>,
                t: Throwable
            ) {
                callback.handleGitHubError()
            }
        })
    }

}
