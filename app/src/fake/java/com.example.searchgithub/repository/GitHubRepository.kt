package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

class GitHubRepository @Inject constructor(@Suppress("unused") private val gitHubApi: GitHubApi) : IGitHubRepository {

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
                    getSearchResponse()
                )
            )
        }
    }

    private fun getSearchResponse(): SearchResponse {
        val list: MutableList<SearchResult> = mutableListOf()
        for (index in 1..100) {
            list.add(
                SearchResult(
                    id = index,
                    name = "Name: $index",
                    fullName = "FullName: $index",
                    private = Random.nextBoolean(),
                    description = "Description: $index",
                    updatedAt = "Updated: $index",
                    size = index,
                    stargazersCount = Random.nextInt(100),
                    language = "",
                    hasWiki = Random.nextBoolean(),
                    archived = Random.nextBoolean(),
                    score = index.toDouble()
                )
            )
        }
        return SearchResponse(list.size, list)
    }

    companion object {
        const val TOTAL_COUNT = 100
        const val RESPONSE_IS_NULL = "ResponseIsNull"
        const val SEARCH_RESULT_IS_NULL = "SearchResultsIsNull"
        const val TOTAL_COUNT_IS_NULL = "TotalCountIsNull"
        const val DISCONNECT_NETWORK = "DisconnectNetwork"
    }

}
