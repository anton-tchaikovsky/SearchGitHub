package com.example.searchgithub

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.GitHubApi
import com.example.searchgithub.repository.GitHubRepository
import com.example.searchgithub.stubs.SchedulerProviderStub
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GitHubRepositoryTest {

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    private val schedulerProvider = SchedulerProviderStub()

    private val compositeDisposable = CompositeDisposable()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchGithub_ResponseIsNull_Test() {
        compositeDisposable.add(repository.searchGithub(GitHubRepository.RESPONSE_IS_NULL)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribeBy(
                onSuccess = {
                    assert(false)
                },
                onError = {
                    assertEquals(it.message, GitHubRepository.RESPONSE_IS_NULL)
                }
            ))
    }

    @Test
    fun searchGithub_SearchResultIsNull_Test() {
        compositeDisposable.add(repository.searchGithub(GitHubRepository.SEARCH_RESULT_IS_NULL)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribeBy(
                onSuccess = {
                    assertEquals(
                        it, SearchResponse(
                            GitHubRepository.TOTAL_COUNT,
                            null
                        )
                    )
                },
                onError = {
                    assert(false)
                }
            ))
    }

    @Test
    fun searchGithub_TotalCountIsNull_Test() {
        compositeDisposable.add(repository.searchGithub(GitHubRepository.TOTAL_COUNT_IS_NULL)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribeBy(
                onSuccess = {
                    assertEquals(
                        it, SearchResponse(
                            null,
                            listOf()
                        )
                    )
                },
                onError = {
                    assert(false)
                }
            ))
    }

    @Test
    fun searchGithub_DisconnectNetwork_Test() {
        compositeDisposable.add(repository.searchGithub(GitHubRepository.DISCONNECT_NETWORK)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribeBy(
                onSuccess = {
                    assert(false)
                },
                onError = {
                    assertEquals(it.message, GitHubRepository.DISCONNECT_NETWORK)
                }
            ))
    }

    @Test
    fun searchGithub_Successful_Test() {
        compositeDisposable.add(repository.searchGithub(SEARCH_TEXT)
            .subscribeOn(schedulerProvider.ioScheduler())
            .observeOn(schedulerProvider.mainScheduler())
            .subscribeBy(
                onSuccess = {
                   assert(true)
                    assertEquals(
                        it.totalCount, repository.getSearchResponse().totalCount
                    )
                },
                onError = {
                    assert(false)
                }
            ))
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
        compositeDisposable.clear()
    }

}