package com.example.searchgithub

import com.example.searchgithub.presenter.details.DetailsPresenter
import com.example.searchgithub.view.details.ViewDetailsContract
import com.nhaarman.mockito_kotlin.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailsPresenterTest {

    private val presenter = DetailsPresenter(TOTAL_COUNT_TEST)

    @Mock
    private lateinit var view: ViewDetailsContract

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun onAttach_FirstAttach_Test() {
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.count, TOTAL_COUNT_TEST)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.times(1)).setCount(TOTAL_COUNT_TEST)
    }

    @Test
    fun onAttach_BeforeRestartViewContract_Test() {
        assertNull(presenter.viewContract)
        presenter.onAttach(view)
        assertEquals(presenter.count, TOTAL_COUNT_TEST)
        assertEquals(presenter.viewContract, view)
        verify(view, Mockito.times(1)).setCount(TOTAL_COUNT_TEST)
    }

    @Test
    fun onDetach_Test() {
        presenter.onAttach(view)
        presenter.onDetach()
        assertEquals(presenter.count, TOTAL_COUNT_TEST)
        assertNull(presenter.viewContract)
    }

    @Test
    fun onIncrement_Test() {
        presenter.onAttach(view)
        presenter.onIncrement()
        assertEquals(presenter.count, TOTAL_COUNT_TEST + TOTAL_COUNT_INCREMENT)
        verify(view, Mockito.times(1)).setCount(TOTAL_COUNT_TEST + TOTAL_COUNT_INCREMENT)
    }

    @Test
    fun onDecrement_Test() {
        presenter.onAttach(view)
        presenter.onDecrement()
        assertEquals(presenter.count, TOTAL_COUNT_TEST + TOTAL_COUNT_DECREMENT)
        verify(view, Mockito.times(1)).setCount(TOTAL_COUNT_TEST + TOTAL_COUNT_DECREMENT)
    }

    @After
    fun tearDown() {
        MockitoAnnotations.openMocks(this).close()
    }

}