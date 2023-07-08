package com.example.searchgithub

import android.content.Context
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.view.details.DetailsActivity
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
class DetailsActivityTest {

    private lateinit var scenario: ActivityScenario<DetailsActivity>

    private lateinit var totalCountTextView: TextView

    private lateinit var decrementButton: Button

    private lateinit var incrementButton: Button

    private lateinit var totalCount: String

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java).onActivity {
            totalCountTextView = it.findViewById(R.id.totalCountTextView)
            decrementButton = it.findViewById(R.id.decrementButton)
            incrementButton = it.findViewById(R.id.incrementButton)
            totalCount = it.getString(R.string.results_count)
        }
    }

    @Test
    fun activity_IsResumed_Test() {
        assertEquals(scenario.state, Lifecycle.State.RESUMED)
        scenario.onActivity {
            assertNotNull(it)
        }
        assertNotNull(totalCountTextView)
        assertEquals(totalCountTextView.visibility, View.VISIBLE)
        assertNotNull(decrementButton)
        assertEquals(decrementButton.visibility, View.VISIBLE)
        assertNotNull(incrementButton)
        assertEquals(incrementButton.visibility, View.VISIBLE)
        assertEquals(totalCountTextView.text, String.format(Locale.getDefault(), totalCount, TOTAL_COUNT_ZERO))
    }

    @Test
    fun activityButtonIncrement_IsWorking() {
        incrementButton.performClick()
        assertEquals(totalCountTextView.text, String.format(Locale.getDefault(), totalCount, TOTAL_COUNT_INCREMENT))
    }

    @Test
    fun activityButtonDecrement_IsWorking() {
        decrementButton.performClick()
        assertEquals(totalCountTextView.text, String.format(Locale.getDefault(), totalCount, TOTAL_COUNT_DECREMENT))
    }

    @Test
    fun activityCreateIntent(){
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = DetailsActivity.getIntent(context, TOTAL_COUNT_TEST)
        val bundle = intent.extras
        assertNotNull(intent)
        assertNotNull(bundle)
        assertEquals(bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, TOTAL_COUNT_ZERO), TOTAL_COUNT_TEST)
    }

    @After
    fun close() {
        scenario.close()
    }

}