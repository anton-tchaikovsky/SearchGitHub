package com.example.searchgithub.view.search


import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.searchgithub.DECREMENT
import com.example.searchgithub.DELAY_SHOT
import com.example.searchgithub.R
import com.example.searchgithub.TOTAL_COUNT_TEXT_DECREMENT
import com.example.searchgithub.TOTAL_COUNT_TEXT_ZERO
import com.example.searchgithub.delay
import com.example.searchgithub.view.details.DetailsActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailsActivityTestRotateScreen {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(DetailsActivity::class.java)

    @Test
    fun detailsActivityTestRotate() {

        val totalCountTextView = onView(
            allOf(
                withId(R.id.totalCountTextView),
                isDisplayed()
            )
        )
        totalCountTextView.check(matches(withText(TOTAL_COUNT_TEXT_ZERO)))

        val decrementButton = onView(
            allOf(
                withId(R.id.decrementButton), withText(DECREMENT),
                isDisplayed()
            )
        )
        decrementButton.perform(click())

        totalCountTextView.check(matches(withText(TOTAL_COUNT_TEXT_DECREMENT)))

        mActivityScenarioRule.scenario.onActivity {
            it.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        onView(isRoot()).perform(delay(DELAY_SHOT))

        val totalCountTextViewRotate = onView(
            allOf(
                withId(R.id.totalCountTextView),
                isDisplayed()
            )
        )
        totalCountTextViewRotate.check(matches(withText(TOTAL_COUNT_TEXT_DECREMENT)))
    }

}
