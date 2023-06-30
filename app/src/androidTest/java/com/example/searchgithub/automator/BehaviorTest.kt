package com.example.searchgithub.automator

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.example.searchgithub.R
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test
import java.util.Locale

class BehaviorTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private lateinit var totalCount: String
    private lateinit var emptyQuery: String

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName).apply {
            this@apply?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
        totalCount = context.getString(R.string.results_count)
        emptyQuery = context.getString(R.string.enter_search_word)
    }

    @Test
    fun mainActivity_IsResumed_Test() {
        assertNotNull(uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT)))
        assertNull(uiDevice.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)))
        assertNotNull(uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON)))
        assertNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
    }

    @Test
    fun mainActivity_SearchText() {
        uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT)).text = SEARCH_TEXT
        Espresso.onView(withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())
        assertNotNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT
        )
        assertNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        assertEquals(totalCountTextView.text.toString(), String.format(
            Locale.getDefault(),
            totalCount,
            TOTAL_COUNT_TEST
        ))
    }

    @Test
    fun mainActivity_OpenDetailsActivity(){
        uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON)).click()
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT
        )
        assertEquals(totalCountTextView.text.toString(), String.format(
            Locale.getDefault(),
            totalCount,
           0
        ))
    }

    companion object {
        private const val TIMEOUT = 5000L
        private const val PROGRESS_BAR = "progressBar"
        private const val TOTAL_COUNT_TEXT_VIEW = "totalCountTextView"
        private const val SEARCH_EDIT_TEXT = "searchEditText"
        private const val TO_DETAILS_ACTIVITY_BUTTON = "toDetailsActivityButton"
        private const val SEARCH_TEXT = "Algol"
        private const val TOTAL_COUNT_TEST = 3804
    }

}