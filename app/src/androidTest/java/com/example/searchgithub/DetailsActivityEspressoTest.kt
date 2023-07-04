package com.example.searchgithub

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.searchgithub.view.details.DetailsActivity
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Locale

class DetailsActivityEspressoTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>

    private lateinit var totalCount: String

    private lateinit var totalCountTextView: ViewInteraction

    private lateinit var decrementButton: ViewInteraction

    private lateinit var incrementButton: ViewInteraction

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java).onActivity {
            totalCount = it.getString(R.string.results_count)
            totalCountTextView =  onView(withId(R.id.totalCountTextView))
            decrementButton = onView(withText(it.getString(R.string.decrement_text)))
            incrementButton = onView(withText(it.getString(R.string.increment_text)))
        }
    }

    @Test
    fun activity_IsResumed_Test() {
        assertEquals(scenario.state, Lifecycle.State.RESUMED)
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activityTextView_HasText() {
       totalCountTextView.check(
            matches(
                withText(
                    String.format(
                        Locale.getDefault(),
                        totalCount,
                        0
                    )
                )
            )
        )
    }

    @Test
    fun activityTextView_IsDisplayed() {
        totalCountTextView.check(matches(isDisplayed()))
    }

    @Test
    fun activityTextView_IsCompletelyDisplayed() {
        totalCountTextView.check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityButtons_AreEffectiveVisible(){
        decrementButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        incrementButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
    }

    @Test
    fun activityButton_IsWorking(){
        decrementButton.perform(click())
        totalCountTextView.check(
            matches(
                withText(
                    String.format(
                        Locale.getDefault(),
                        totalCount,
                        -1
                    )
                )
            )
        )
        incrementButton.perform(click())
        totalCountTextView.check(
            matches(
                withText(
                    String.format(
                        Locale.getDefault(),
                        totalCount,
                        0
                    )
                )
            )
        )
    }

    @After
    fun close() {
        scenario.close()
    }

}