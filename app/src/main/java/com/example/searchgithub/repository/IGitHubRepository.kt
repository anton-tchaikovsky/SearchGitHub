package com.example.searchgithub.repository

interface IGitHubRepository {
    fun searchGithub(
        query: String,
        callback: GitHubRepositoryCallback
    )
}