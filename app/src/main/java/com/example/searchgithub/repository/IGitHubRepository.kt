package com.example.searchgithub.repository

import com.example.searchgithub.model.SearchResponse

interface IGitHubRepository {
    suspend fun searchGithub(
        query: String
    ): SearchResponse
}