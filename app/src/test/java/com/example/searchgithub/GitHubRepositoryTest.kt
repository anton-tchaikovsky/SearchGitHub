package com.example.searchgithub

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.GitHubApi
import com.example.searchgithub.repository.GitHubRepository
import com.example.searchgithub.repository.GitHubRepositoryCallback
import com.nhaarman.mockito_kotlin.verify
import okhttp3.Request
import okio.Timeout
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        repository.searchGithub(SEARCH_TEXT, gitHubRepositoryCallback)
        verify(gitHubApi, Mockito.times(1)).searchGithub(SEARCH_TEXT)
    }

    @Test
    fun searchGithub_CallbackResponse_Test(){
        val call = object: Call<SearchResponse?>{
            override fun clone(): Call<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

            override fun enqueue(callback: Callback<SearchResponse?>) {
                callback.onResponse(this, response)
            }

        }
        Mockito.`when`(gitHubApi.searchGithub(SEARCH_TEXT)).thenReturn(call)
        repository.searchGithub(SEARCH_TEXT, gitHubRepositoryCallback)
        verify(gitHubRepositoryCallback, Mockito.times(1)).handleGitHubResponse(response)

    }

    @Test
    fun searchGithub_CallbackFailure_Test(){
        val call = object: Call<SearchResponse?>{
            override fun clone(): Call<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<SearchResponse?> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
            }

            override fun enqueue(callback: Callback<SearchResponse?>) {
                callback.onFailure(this, throwable)
            }

        }
        Mockito.`when`(gitHubApi.searchGithub(SEARCH_TEXT)).thenReturn(call)
        repository.searchGithub(SEARCH_TEXT, gitHubRepositoryCallback)
        verify(gitHubRepositoryCallback, Mockito.times(1)).handleGitHubError()

    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

    companion object {
        private val gitHubRepositoryCallback = Mockito.mock(GitHubRepositoryCallback::class.java)
        @Suppress("UNCHECKED_CAST")
        private val response = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        private val throwable = Mockito.mock(Throwable::class.java)
    }

}