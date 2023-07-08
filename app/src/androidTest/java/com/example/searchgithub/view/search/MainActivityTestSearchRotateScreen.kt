package com.example.searchgithub.view.search


import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.searchgithub.DELAY_LONG
import com.example.searchgithub.DELAY_SHOT
import com.example.searchgithub.R
import com.example.searchgithub.SEARCH_TEXT
import com.example.searchgithub.SEARCH_WORD
import com.example.searchgithub.TOTAL_COUNT_TEXT_TEST
import com.example.searchgithub.delay
import com.example.searchgithub.isKeyboardOpened
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTestSearchRotateScreen {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun mainActivityTestSearchRotate() {

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

        mActivityScenarioRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(isRoot()).perform(delay(DELAY_SHOT))

        if (isKeyboardOpened())
            pressBack()

        val totalCountTextView = onView(
            allOf(
                withId(R.id.totalCountTextView),
                isDisplayed()
            )
        )
        totalCountTextView.check(matches(withText(TOTAL_COUNT_TEXT_TEST)))

        val searchEditTextRotate = onView(
            allOf(
                withId(R.id.searchEditText),
                isDisplayed()
            )
        )
        searchEditTextRotate.check(matches(withText(SEARCH_TEXT)))
    }

}
