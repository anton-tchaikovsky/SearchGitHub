package com.example.searchgithub

import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragment
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.view.details.DetailsFragment
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.Locale

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.N])
class DetailsFragmentTest {

    private lateinit var scenario: FragmentScenario<DetailsFragment>

    private var totalCountTextView: TextView? = null

    private var decrementButton: Button? = null

    private var incrementButton: Button? = null

    private lateinit var totalCount: String

    @Before
    fun setup() {
        scenario = launchFragment<DetailsFragment>().onFragment {
            totalCountTextView = it.view?.findViewById(R.id.totalCountTextView)
            decrementButton = it.view?.findViewById (R.id.decrementButton)
            incrementButton = it.view?.findViewById (R.id.incrementButton)
            totalCount = it.getString (R.string.results_count)
        }
    }

    @Test
    fun fragment_IsResumed_Test() {
        scenario.onFragment {
            assertNotNull(it)
        }
        assertNotNull(totalCountTextView)
        assertEquals(totalCountTextView?.visibility, View.VISIBLE)
        assertNotNull(decrementButton)
        assertEquals(decrementButton?.visibility, View.VISIBLE)
        assertNotNull(incrementButton)
        assertEquals(incrementButton?.visibility, View.VISIBLE)
        assertEquals(
            totalCountTextView?.text,
            String.format(Locale.getDefault(), totalCount, TOTAL_COUNT_ZERO)
        )
    }

    @Test
    fun fragment_ButtonIncrement_IsWorking() {
        incrementButton?.performClick()
        assertEquals(
            totalCountTextView?.text,
            String.format(Locale.getDefault(), totalCount, TOTAL_COUNT_INCREMENT)
        )
    }

    @Test
    fun fragment_ButtonDecrement_IsWorking() {
        decrementButton?.performClick()
        assertEquals(
            totalCountTextView?.text,
            String.format(Locale.getDefault(), totalCount, TOTAL_COUNT_DECREMENT)
        )
    }

    @After
    fun close() {
        scenario.close()
    }

}