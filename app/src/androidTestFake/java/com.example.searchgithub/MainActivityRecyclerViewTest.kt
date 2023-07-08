package com.example.searchgithub

import android.view.View
import android.widget.CheckBox
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.view.search.MainActivity
import com.example.searchgithub.view.search.SearchResultAdapter
import junit.framework.TestCase.assertNotNull
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityRecyclerViewTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.searchEditText)).perform(click())
            .perform(replaceText(SEARCH_TEXT),closeSoftKeyboard())
            .perform(pressImeActionButton())
    }

    @Test
    fun activityRecyclerView_ScrollTo(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<SearchResultAdapter.SearchResultViewHolder>(
            RECYCLE_VIEW_POSITION_TEST))
        assertNotNull(onView(withText(RECYCLE_VIEW_FULL_NAME_FAKE)))
    }

    @Test
    fun activityRecyclerView_PerformClickItem(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<SearchResultAdapter.SearchResultViewHolder>(
            RECYCLE_VIEW_POSITION_TEST))
            .perform(RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
                hasDescendant(withText(RECYCLE_VIEW_FULL_NAME_FAKE)),
                click()
            ))
        onView(withText(RECYCLE_VIEW_FULL_NAME_FAKE)).inRoot(ToastMatcher())
            .check(matches(isDisplayed()))
    }

    @Test
    fun activityRecyclerView_PerformClickCheckbox(){
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition<SearchResultAdapter.SearchResultViewHolder>(
            RECYCLE_VIEW_POSITION_TEST))
            .perform(RecyclerViewActions.actionOnItem<SearchResultAdapter.SearchResultViewHolder>(
                hasDescendant(withText(RECYCLE_VIEW_FULL_NAME_FAKE)),
                tapOnItemWithId(R.id.checkbox)
            ))
    }

    private fun tapOnItemWithId(id: Int) = object : ViewAction {
        override fun getConstraints(): Matcher<View>? {
            return null
        }
        override fun getDescription(): String {
            return "Tap on item with id"
        }
        override fun perform(uiController: UiController, view: View) {
            val v = view.findViewById(id) as CheckBox
            v.performClick()
        }
    }

    @After
    fun close() {
        scenario.close()
    }

}