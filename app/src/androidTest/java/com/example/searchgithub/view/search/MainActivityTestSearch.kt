package com.example.searchgithub.view.search


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.searchgithub.DELAY_LONG
import com.example.searchgithub.R
import com.example.searchgithub.SEARCH_TEXT
import com.example.searchgithub.SEARCH_WORD
import com.example.searchgithub.TOTAL_COUNT_TEXT_TEST
import com.example.searchgithub.delay
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTestSearch {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTestSearch() {

        val searchEditText = onView(
            allOf(
                withId(R.id.searchEditText),
                isDisplayed()
            )
        )
        searchEditText.perform(replaceText(SEARCH_TEXT), closeSoftKeyboard())

        val searchFAB = onView(
            allOf(
                withId(R.id.searchFloatingActionButton), withContentDescription(SEARCH_WORD),
                isDisplayed()
            )
        )
        searchFAB.perform(click())
        onView(isRoot()).perform(delay(DELAY_LONG))

        val totalCountTextView = onView(
            allOf(
                withId(R.id.totalCountTextView),
                isDisplayed()
            )
        )
        totalCountTextView.check(matches(withText(TOTAL_COUNT_TEXT_TEST)))
    }

}
