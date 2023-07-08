package com.example.searchgithub

import com.example.searchgithub.model.SearchResponse
import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.presenter.search.SearchPresenter
import com.example.searchgithub.repository.IGitHubRepository
import com.example.searchgithub.view.search.ViewSearchContract
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.inOrder
import com.nhaarman.mockito_kotlin.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class SearchPresenterTest {

    @Mock
    private lateinit var repository: IGitHubRepository

    @Mock
    private lateinit var view: ViewSearchContract

    private lateinit var presenter: SearchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        presenter = SearchPresenter(repository)
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
        presenter.searchResults = searchResult_Test
        presenter.totalCount = TOTAL_COUNT_TEST
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.searchResults, searchResult_Test)
        assertEquals(presenter.totalCount, TOTAL_COUNT_TEST)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.times(1)).displaySearchResults(searchResult_Test, TOTAL_COUNT_TEST)
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
        presenter.searchResults = searchResult_Test
        presenter.totalCount = TOTAL_COUNT_ZERO
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.searchResults, searchResult_Test)
        assertEquals(presenter.totalCount, TOTAL_COUNT_ZERO)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.never()).displaySearchResults(any(), any())
    }

    @Test
    fun onDetach_Test() {
        presenter.onAttach(view)
        presenter.searchResults = searchResult_Test
        presenter.totalCount = TOTAL_COUNT_TEST
        presenter.onDetach()
        assertEquals(presenter.searchResults, searchResult_Test)
        assertEquals(presenter.totalCount, TOTAL_COUNT_TEST)
        assertNull(presenter.viewContract)
    }

    @Test
    fun searchGitHub_Test() {
        presenter.onAttach(view)
        presenter.searchGitHub(SEARCH_TEXT)
        verify(view, Mockito.times(1)).displayLoading(true)
        verify(repository, Mockito.times(1)).searchGithub(SEARCH_TEXT, presenter)
    }

    @Test
    fun handleGitHubError_Test() {
        presenter.onAttach(view)
        presenter.handleGitHubError()
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.times(1)).displayError()
        }
    }

    @Test
    fun handleGitHubResponse_ResponseUnsuccessful_Test() {
        presenter.onAttach(view)
        Mockito.`when`(responseTest.isSuccessful).thenReturn(false)
        presenter.handleGitHubResponse(responseTest)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(RESPONSE_UNSUCCESSFUL)
        }
    }

    @Test
    fun handleGitHubResponse_ResponseNull_Test() {
        presenter.onAttach(view)
        presenter.handleGitHubResponse(null)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(RESPONSE_UNSUCCESSFUL)
        }
    }

    @Test
    fun handleGitHubResponse_ResponseBodyNull_Test() {
        presenter.onAttach(view)
        Mockito.`when`(responseTest.isSuccessful).thenReturn(true)
        Mockito.`when`(responseTest.body()).thenReturn(null)
        presenter.handleGitHubResponse(responseTest)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun handleGitHubResponse_SearchResultsNull_Test() {
        presenter.onAttach(view)
        Mockito.`when`(responseTest.isSuccessful).thenReturn(true)
        Mockito.`when`(responseTest.body()).thenReturn(searchResponse)
        Mockito.`when`(searchResponse.searchResults).thenReturn(null)
        Mockito.`when`(searchResponse.totalCount).thenReturn(TOTAL_COUNT_TEST)
        presenter.handleGitHubResponse(responseTest)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun handleGitHubResponse_TotalCountNull_Test() {
        presenter.onAttach(view)
        Mockito.`when`(responseTest.isSuccessful).thenReturn(true)
        Mockito.`when`(responseTest.body()).thenReturn(searchResponse)
        Mockito.`when`(searchResponse.searchResults).thenReturn(searchResult_Test)
        Mockito.`when`(searchResponse.totalCount).thenReturn(null)
        presenter.handleGitHubResponse(responseTest)
        inOrder(view).apply {
            verify(view, Mockito.times(1)).displayLoading(false)
            verify(view, Mockito.never()).displaySearchResults(any(), any())
            verify(view, Mockito.times(1)).displayError(SEARCH_RESULT_OR_TOTAL_COUNT_NULL)
        }
    }

    @Test
    fun handleGitHubResponse_Successful_Test() {
        presenter.onAttach(view)
        Mockito.`when`(responseTest.isSuccessful).thenReturn(true)
        Mockito.`when`(responseTest.body()).thenReturn(searchResponse)
        Mockito.`when`(searchResponse.searchResults).thenReturn(searchResult_Test)
        Mockito.`when`(searchResponse.totalCount).thenReturn(TOTAL_COUNT_TEST)
        presenter.handleGitHubResponse(responseTest)
        inOrder(view).apply {
            com.nhaarman.mockito_kotlin.verify(view, Mockito.times(1)).displayLoading(false)
            com.nhaarman.mockito_kotlin.verify(view, Mockito.times(1))
                .displaySearchResults(searchResult_Test, TOTAL_COUNT_TEST)
            com.nhaarman.mockito_kotlin.verify(view, Mockito.never()).displayError(any())
        }
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

    companion object {
        private val searchResult_Test = listOf(
            Mockito.mock(
                SearchResult::class
                    .java
            )
        )

        @Suppress("UNCHECKED_CAST")
        private val responseTest = Mockito.mock(Response::class.java) as Response<SearchResponse?>
        private val searchResponse = Mockito.mock(SearchResponse::class.java)
    }

}