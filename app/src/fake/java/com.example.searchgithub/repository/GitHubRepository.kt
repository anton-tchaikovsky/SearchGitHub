package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import retrofit2.Response

internal class GitHubRepository(@Suppress("unused") private val gitHubApi: GitHubApi) : IGitHubRepository {

    override fun searchGithub(
        query: String,
        callback: GitHubRepositoryCallback
    ) {
        when (query) {
            RESPONSE_IS_NULL -> callback.handleGitHubResponse(null)
            SEARCH_RESULT_IS_NULL -> callback.handleGitHubResponse(
                Response.success(
                    SearchResponse(
                        TOTAL_COUNT,
                        null
                    )
                )
            )

            TOTAL_COUNT_IS_NULL -> callback.handleGitHubResponse(
                Response.success(
                    SearchResponse(
                        null,
                        listOf()
                    )
                )
            )

            DISCONNECT_NETWORK -> callback.handleGitHubError()
            else -> callback.handleGitHubResponse(
                Response.success(
                    SearchResponse(
                        TOTAL_COUNT,
                        listOf()
                    )
                )
            )
        }
    }

    companion object {
        const val TOTAL_COUNT = 100
        const val RESPONSE_IS_NULL = "ResponseIsNull"
        const val SEARCH_RESULT_IS_NULL = "SearchResultsIsNull"
        const val TOTAL_COUNT_IS_NULL = "TotalCountIsNull"
        const val DISCONNECT_NETWORK = "DisconnectNetwork"
    }

}
