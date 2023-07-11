package com.example.searchgithub

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.presenter.search.SearchPresenter
import com.example.searchgithub.repository.IGitHubRepository
import com.example.searchgithub.stubs.SchedulerProviderStub
import com.example.searchgithub.view.search.ViewSearchContract
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.rxjava3.core.Single
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.io.IOException

class SearchPresenterTest {

    @Mock
    private lateinit var repository: IGitHubRepository

    @Mock
    private lateinit var view: ViewSearchContract

    private lateinit var presenter: SearchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        presenter = SearchPresenter(repository, SchedulerProviderStub())
    }

    @Test
    fun onAttach_FirstAttach_Test() {
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.searchResults, listOf<SearchResult>())
        assertEquals(presenter.totalCount, TOTAL_COUNT_ZERO)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.never()).displaySearchResults(any(), any())
    }

    @Test
    fun onAttach_BeforeRestartViewContract_Test() {
        presenter.searchResults = searchResultTest
        presenter.totalCount = TOTAL_COUNT_TEST
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.searchResults, searchResultTest)
        assertEquals(presenter.totalCount, TOTAL_COUNT_TEST)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.times(1)).displaySearchResults(searchResultTest, TOTAL_COUNT_TEST)
    }

    @Test
    fun onAttach_BeforeRestartViewContract_SearchResultEmpty_Test() {
        presenter.searchResults = listOf()
        presenter.totalCount = TOTAL_COUNT_TEST
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.searchResults, listOf<SearchResult>())
        assertEquals(presenter.totalCount, TOTAL_COUNT_TEST)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.never()).displaySearchResults(any(), any())
    }

    @Test
    fun onAttach_BeforeRestartViewContract_TotalCountZero_Test() {
        presenter.searchResults = searchResultTest
        presenter.totalCount = TOTAL_COUNT_ZERO
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.searchResults, searchResultTest)
        assertEquals(presenter.totalCount, TOTAL_COUNT_ZERO)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.never()).displaySearchResults(any(), any())
    }

    @Test
    fun onDetach_Test() {
        presenter.onAttach(view)
        presenter.searchResults = searchResultTest
        presenter.totalCount = TOTAL_COUNT_TEST
        presenter.onDetach()
        assertEquals(presenter.searchResults, searchResultTest)
        assertEquals(presenter.totalCount, TOTAL_COUNT_TEST)
        assertNull(presenter.viewContract)
    }

    @Test
    fun searchGitHub_Error_WithMessage_Test() {
        Mockito.`when`(
            repository.searchGithub(SEARCH_TEXT)
        ).thenReturn(
            Single.error(IOException(ERROR_MESSAGE_TEST))
        )
        presenter.onAttach(view)
        presenter.searchGitHub(SEARCH_TEXT)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(true)
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(ERROR_MESSAGE_TEST)
        }
    }

    @Test
    fun searchGitHub_Error_WithoutMessage_Test() {
        Mockito.`when`(
            repository.searchGithub(SEARCH_TEXT)
        ).thenReturn(
            Single.error(IOException())
        )
        presenter.onAttach(view)
        presenter.searchGitHub(SEARCH_TEXT)
        verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(true)
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(RESPONSE_UNSUCCESSFUL)
        }
    }

    @Test
    fun searchGitHub_Successful_SearchResultsIsNull_Test() {
        Mockito.`when`(repository.searchGithub(SEARCH_TEXT)).thenReturn(
            Single.just(
                SearchResponse(TOTAL_COUNT_TEST, null)
            )
        )
        presenter.onAttach(view)
        presenter.searchGitHub(SEARCH_TEXT)
        verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(true)
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun searchGitHub_Successful_TotalCountIsNull_Test() {
        Mockito.`when`(repository.searchGithub(SEARCH_TEXT)).thenReturn(
            Single.just(
                SearchResponse(null, searchResultTest)
            )
        )
        presenter.onAttach(view)
        presenter.searchGitHub(SEARCH_TEXT)
        verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(true)
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun searchGitHub_Successful_Test() {
        Mockito.`when`(repository.searchGithub(SEARCH_TEXT)).thenReturn(
            Single.just(
                SearchResponse(TOTAL_COUNT_TEST, searchResultTest)
            )
        )
        presenter.onAttach(view)
        presenter.searchGitHub(SEARCH_TEXT)
        verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(true)
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.times(1)).displaySearchResults(searchResultTest, TOTAL_COUNT_TEST)
            verify(view, Mockito.never()).displayError(any())
        }
    }

    @After
    fun tearDown() {
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