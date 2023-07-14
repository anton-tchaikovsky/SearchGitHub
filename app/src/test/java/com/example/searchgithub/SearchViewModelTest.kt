package com.example.searchgithub

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.model.AppState
import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.repository.IGitHubRepository
import com.example.searchgithub.rule.TestCoroutineRule
import com.example.searchgithub.view_model.SearchViewModel
import com.nhaarman.mockito_kotlin.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.annotation.Config

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class SearchViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var repository: IGitHubRepository

    private lateinit var viewModel: SearchViewModel

    private val observer = Observer<AppState> {}

    private lateinit var liveDataTest: LiveData<AppState>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SearchViewModel(repository)
        liveDataTest = viewModel.getLiveData().also {
            it.observeForever(observer)
        }
    }

    @Test
    fun searchGitHub_Error_Test() {
        testCoroutineRule.runBlockingTest {
            viewModel.searchGitHub(SEARCH_TEXT)
            verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
            val value = liveDataTest.value as AppState.Error
            assertEquals(
                value.Error.message,
                "Cannot invoke \"com.example.searchgithub.model.SearchResponse.getSearchResults()\" because \"it\" is null"
            )
        }
    }

    @Test
    fun searchGitHub_Successful_SearchResultsIsNull_Test() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.searchGithub(SEARCH_TEXT)).thenReturn(
                SearchResponse(TOTAL_COUNT_TEST, null)
            )
            viewModel.searchGitHub(SEARCH_TEXT)
            verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
            val value = liveDataTest.value as AppState.Error
            assertEquals(value.Error.message, SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun searchGitHub_Successful_TotalCountIsNull_Test() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.searchGithub(SEARCH_TEXT)).thenReturn(
                SearchResponse(null, searchResultTest)
            )
            viewModel.searchGitHub(SEARCH_TEXT)
            verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
            val value = liveDataTest.value as AppState.Error
            assertEquals(value.Error.message, SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun searchGitHub_Successful_Test() {
        testCoroutineRule.runBlockingTest {
            Mockito.`when`(repository.searchGithub(SEARCH_TEXT)).thenReturn(
                SearchResponse(TOTAL_COUNT_TEST, searchResultTest)
            )
            viewModel.searchGitHub(SEARCH_TEXT)
            verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
            val value = liveDataTest.value as AppState.Success
            assertEquals(value.SearchResponse, SearchResponse(TOTAL_COUNT_TEST, searchResultTest))
        }
    }

    @After
    fun tearDown() {
        liveDataTest.removeObserver(observer)
        MockitoAnnotations.openMocks(this).close()
    }

    companion object {
        private val searchResultTest = listOf(
            Mockito.mock(
                SearchResult::class
                    .java
            )
        )
    }

}