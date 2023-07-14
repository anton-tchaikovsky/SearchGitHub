package com.example.searchgithub

import android.os.Build
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.repository.GitHubApi
import com.example.searchgithub.repository.GitHubRepository
import com.example.searchgithub.rule.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class GitHubRepositoryTest {

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var repository: GitHubRepository

    @Mock
    private lateinit var gitHubApi: GitHubApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = GitHubRepository(gitHubApi)
    }

    @Test
    fun searchGithub_ResponseIsNull_Test() {
        testCoroutineRule.runBlockingTest {
            try {
                repository.searchGithub(GitHubRepository.RESPONSE_IS_NULL)
            } catch (e: IOException) {
                assertEquals(
                    e.message, GitHubRepository.RESPONSE_IS_NULL
                )
            }
        }
    }

    @Test
    fun searchGithub_SearchResultIsNull_Test() {
        testCoroutineRule.runBlockingTest {
            assertEquals(
                repository.searchGithub(GitHubRepository.SEARCH_RESULT_IS_NULL), SearchResponse(
                    GitHubRepository.TOTAL_COUNT,
                    null
                )
            )
        }
    }

    @Test
    fun searchGithub_TotalCountIsNull_Test() {
        testCoroutineRule.runBlockingTest {
            assertEquals(
                repository.searchGithub(GitHubRepository.TOTAL_COUNT_IS_NULL), SearchResponse(
                    null,
                    listOf()
                )
            )
        }
    }

    @Test
    fun searchGithub_DisconnectNetwork_Test() {
        testCoroutineRule.runBlockingTest {
            try {
                repository.searchGithub(GitHubRepository.DISCONNECT_NETWORK)
            } catch (e: IOException) {
                assertEquals(
                    e.message, GitHubRepository.DISCONNECT_NETWORK
                )
            }
        }
    }

    @Test
    fun searchGithub_Successful_Test() {
        testCoroutineRule.runBlockingTest {
            assertEquals(
                repository.searchGithub(SEARCH_TEXT).totalCount,
                repository.getSearchResponse().totalCount
            )
        }
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

}