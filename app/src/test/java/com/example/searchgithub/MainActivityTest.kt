package com.example.searchgithub

import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.view.search.MainActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.N])
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    private lateinit var searchEditText: EditText

    private lateinit var searchFAB: FloatingActionButton

    private lateinit var toDetailsActivityButton: Button

    private lateinit var progressBar: ProgressBar

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java).onActivity {
            searchEditText = it.findViewById(R.id.searchEditText)
            toDetailsActivityButton = it.findViewById(R.id.toDetailsActivityButton)
            progressBar = it.findViewById(R.id.progressBar)
            searchFAB = it.findViewById(R.id.searchFloatingActionButton)
        }
    }

    @Test
    fun activity_IsResumed_Test() {
        assertEquals(scenario.state, Lifecycle.State.RESUMED)
        scenario.onActivity {
            assertNotNull(it)
        }
        assertNotNull(searchEditText)
        assertEquals(searchEditText.visibility, View.VISIBLE)
        assertNotNull(toDetailsActivityButton)
        assertEquals(toDetailsActivityButton.visibility, View.VISIBLE)
        assertNotNull(progressBar)
        assertEquals(progressBar.visibility, View.GONE)
        assertNotNull(searchFAB)
        assertEquals(searchFAB.visibility, View.VISIBLE)
        assertTrue(searchEditText.text.isEmpty())
    }

    @Test
    fun activity_SearchText() {
        searchEditText.setText(SEARCH_TEXT)
        searchEditText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
        assertEquals(searchEditText.text.toString(), SEARCH_TEXT)
        assertEquals(progressBar.visibility, View.VISIBLE)
    }

    @Test
    fun activity_SearchTextEmpty() {
        searchEditText.setText(SEARCH_EMPTY_TEXT)
        searchEditText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
        assertEquals(progressBar.visibility, View.GONE)
        scenario.onActivity {
            assertEquals(
                ShadowToast.getTextOfLatestToast(),
                it.getString(R.string.enter_search_word)
            )
        }
    }

    @Test
    fun activity_SearchTextBlank() {
        searchEditText.setText(SEARCH_BLANK_TEXT)
        searchEditText.onEditorAction(EditorInfo.IME_ACTION_SEARCH)
        assertEquals(progressBar.visibility, View.GONE)
        scenario.onActivity {
            assertEquals(
                ShadowToast.getTextOfLatestToast(),
                it.getString(R.string.enter_search_word)
            )
        }
    }

    @Test
    fun activity_SearchText_SearchFAB() {
        searchEditText.setText(SEARCH_EMPTY_TEXT)
        searchEditText.setText(SEARCH_TEXT)
        searchFAB.performClick()
        assertEquals(searchEditText.text.toString(), SEARCH_TEXT)
        assertEquals(progressBar.visibility, View.VISIBLE)
    }

    @Test
    fun activity_SearchTextEmpty_SearchFAB() {
        searchFAB.performClick()
        assertEquals(progressBar.visibility, View.GONE)
        scenario.onActivity {
            assertEquals(
                ShadowToast.getTextOfLatestToast(),
                it.getString(R.string.enter_search_word)
            )
        }
    }

    @Test
    fun activity_SearchTextBlank_SearchFAB() {
        searchEditText.setText(SEARCH_BLANK_TEXT)
        searchFAB.performClick()
        assertEquals(progressBar.visibility, View.GONE)
        scenario.onActivity {
            assertEquals(
                ShadowToast.getTextOfLatestToast(),
                it.getString(R.string.enter_search_word)
            )
        }
    }

    @After
    fun close() {
        scenario.close()
    }

}