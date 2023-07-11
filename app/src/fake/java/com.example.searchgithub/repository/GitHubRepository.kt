package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import io.reactivex.rxjava3.core.Single
import java.io.IOException
import javax.inject.Inject
import kotlin.random.Random

class GitHubRepository @Inject constructor(@Suppress("unused") private val gitHubApi: GitHubApi) :
    IGitHubRepository {

    override fun searchGithub(query: String): Single<SearchResponse> =
        Single.create {
            when (query) {
                RESPONSE_IS_NULL -> it.onError(IOException(RESPONSE_IS_NULL))
                SEARCH_RESULT_IS_NULL -> it.onSuccess(
                    SearchResponse(
                        TOTAL_COUNT,
                        null
                    )
                )

                TOTAL_COUNT_IS_NULL -> it.onSuccess(
                    SearchResponse(
                        null,
                        listOf()
                    )
                )

                DISCONNECT_NETWORK -> it.onError(IOException(DISCONNECT_NETWORK))
                else -> it.onSuccess(
                    getSearchResponse()
                )
            }
        }

    internal fun getSearchResponse(): SearchResponse {
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
