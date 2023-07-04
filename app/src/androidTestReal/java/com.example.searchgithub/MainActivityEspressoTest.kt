package com.example.searchgithub

import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.searchgithub.view.search.MainActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Locale
import org.hamcrest.Matcher

class MainActivityEspressoTest {

    private lateinit var searchEditText: ViewInteraction

    private lateinit var toDetailsActivityButton: ViewInteraction

    private lateinit var progressBar: ViewInteraction

    private lateinit var totalCountTextView: ViewInteraction

    private lateinit var totalCount: String

    private lateinit var emptyQuery: String

    private lateinit var undefinedError: String


    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java).onActivity {
            totalCount = it.getString(R.string.results_count)
            emptyQuery = it.getString(R.string.enter_search_word)
            undefinedError = it.getString(R.string.undefined_error)
            totalCountTextView = onView(withId(R.id.totalCountTextView))
            searchEditText = onView(withId(R.id.searchEditText))
            toDetailsActivityButton = onView(withId(R.id.toDetailsActivityButton))
            progressBar = onView(withId(R.id.progressBar))
        }
    }

    @Test
    fun activity_IsResumed_Test() {
        assertEquals(scenario.state, Lifecycle.State.RESUMED)
        scenario.onActivity {
            assertNotNull(it)
        }
        searchEditText.check(matches(isCompletelyDisplayed())).check(matches(isClickable()))
        toDetailsActivityButton.check(matches(isCompletelyDisplayed()))
            .check(matches(isClickable()))
        progressBar.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        totalCountTextView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
    }

    @Test
    fun activity_SearchText() {
        searchEditText.perform(click()).perform(replaceText(SEARCH_TEXT), closeSoftKeyboard())
            .perform(pressImeActionButton())
        progressBar.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(isRoot()).perform(delay())
        progressBar.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        totalCountTextView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        totalCountTextView.check(
            matches(
                withText(
                    String.format(
                        Locale.getDefault(),
                        totalCount,
                        TOTAL_COUNT_TEST
                    )
                )
            )
        )
    }

    @Test
    fun activity_SearchTextEmpty(){
        searchEditText.perform(click()).perform(replaceText(""), closeSoftKeyboard())
            .perform(pressImeActionButton())
        progressBar.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        totalCountTextView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        onView(withText(emptyQuery)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun activity_SearchTextBlank(){
        searchEditText.perform(click()).perform(replaceText(" "), closeSoftKeyboard())
            .perform(pressImeActionButton())
        progressBar.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        totalCountTextView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
        onView(withText(emptyQuery)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    /**
     * проверять при отключенном интернете на устройстве
     */
    @Test
    fun activity_SearchText_DisconnectNetwork() {
        searchEditText.perform(click()).perform(replaceText(SEARCH_TEXT), closeSoftKeyboard())
            .perform(pressImeActionButton())
        onView(withText(undefinedError)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
        progressBar.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        totalCountTextView.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
    }

    @After
    fun close() {
        scenario.close()
    }

    private fun delay(): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $3 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(DELAY)
            }
        }
    }

    companion object {
        private const val SEARCH_TEXT = "Algol"
        private const val TOTAL_COUNT_TEST = 3804
        private const val DELAY = 3000L
    }

}