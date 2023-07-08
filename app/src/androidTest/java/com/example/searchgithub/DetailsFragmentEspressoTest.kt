package com.example.searchgithub

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.searchgithub.view.details.DetailsFragment
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Locale

class DetailsFragmentEspressoTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    private lateinit var totalCountTextView: ViewInteraction

    private lateinit var decrementButton: ViewInteraction

    private lateinit var incrementButton: ViewInteraction

    private lateinit var totalCount: String

    @Before
    fun setup() {
        scenario = launchFragmentInContainer<DetailsFragment>(bundleOf(Pair(DetailsFragment.TOTAL_COUNT_EXTRA, TOTAL_COUNT_TEST))).onFragment {
            totalCountTextView = onView(withId(R.id.totalCountTextView))
            decrementButton = onView(ViewMatchers.withText(it.getString(R.string.decrement_text)))
            incrementButton = onView(ViewMatchers.withText(it.getString(R.string.increment_text)))
            totalCount = it.getString (R.string.results_count)
        }
    }

    @Test
    fun fragment_IsResumed_Test(){
        scenario.onFragment{
            assertNotNull(it)
        }
        totalCountTextView.check(matches(ViewMatchers.isDisplayed()))
            .check(
                matches(
                    ViewMatchers.withText(
                        String.format(
                            Locale.getDefault(),
                            totalCount,
                            TOTAL_COUNT_TEST
                        )
                    )
                )
            )
        decrementButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(matches(isClickable()))
        incrementButton.check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .check(matches(isClickable()))
    }

    @Test
    fun fragmentButton_IsWorking(){
        decrementButton.perform(ViewActions.click())
        totalCountTextView.check(
            matches(
                ViewMatchers.withText(
                    String.format(
                        Locale.getDefault(),
                        totalCount,
                        TOTAL_COUNT_TEST + TOTAL_COUNT_DECREMENT
                    )
                )
            )
        )
        incrementButton.perform(ViewActions.click())
        totalCountTextView.check(
            matches(
                ViewMatchers.withText(
                    String.format(
                        Locale.getDefault(),
                        totalCount,
                        TOTAL_COUNT_TEST
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