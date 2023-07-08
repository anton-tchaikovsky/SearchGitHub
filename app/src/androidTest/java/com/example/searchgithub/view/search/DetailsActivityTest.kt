package com.example.searchgithub.view.search


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.searchgithub.INCREMENT
import com.example.searchgithub.R
import com.example.searchgithub.TOTAL_COUNT_TEXT_INCREMENT
import com.example.searchgithub.view.details.DetailsActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailsActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(DetailsActivity::class.java)

    @Test
    fun detailsActivityTest() {

        val incrementButton = onView(
            allOf(
                withId(R.id.incrementButton), withText(INCREMENT),
                isDisplayed()
            )
        )
       incrementButton.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.totalCountTextView),
                isDisplayed()
            )
        )
        textView.check(matches(withText(TOTAL_COUNT_TEXT_INCREMENT)))
    }

}
