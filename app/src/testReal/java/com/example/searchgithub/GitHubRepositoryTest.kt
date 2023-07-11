package com.example.searchgithub

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.GitHubApi
import com.example.searchgithub.repository.GitHubRepository
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GitHubRepositoryTest {

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchGithub_Test() {
        Mockito.`when`(gitHubApi.searchGithub(SEARCH_TEXT)).thenReturn(
            Single.just(
                SearchResponse(TOTAL_COUNT_TEST, listOf())
            )
        )
        repository.searchGithub(SEARCH_TEXT)
        verify(gitHubApi, Mockito.times(1)).searchGithub(SEARCH_TEXT)
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

}