package com.example.searchgithub.automator

import android.content.Context
import android.content.Intent
import android.view.accessibility.AccessibilityWindowInfo
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
import junit.framework.TestCase.assertTrue
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
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT_LONG)
        totalCount = context.getString(R.string.results_count)
        emptyQuery = context.getString(R.string.enter_search_word)
    }

    @Test
    fun mainActivity_IsResumed_Test() {
        assertNotNull(uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT)))
        assertNull(uiDevice.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)))
        assertNotNull(uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON)))
        assertNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        assertNotNull(uiDevice.findObject(By.res(packageName, SEARCH_FAB)))
    }

    @Test
    fun mainActivity_SearchText() {
        uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT)).text = SEARCH_TEXT
        Espresso.onView(withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())
        assertNotNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT_LONG
        )
        assertNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        assertNotNull(totalCountTextView)
        assertTrue(totalCountTextView.text.isNotBlank())
    }

    @Test
    fun mainActivity_SearchText_SearchFAB() {
        uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT)).text = SEARCH_TEXT
        uiDevice.findObject(By.res(packageName, SEARCH_FAB)).click()
        assertNotNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT_LONG
        )
        assertNull(uiDevice.findObject(By.res(packageName, PROGRESS_BAR)))
        assertNotNull(totalCountTextView)
        assertTrue(totalCountTextView.text.isNotBlank())
    }

    @Test
    fun mainActivity_OpenDetailsActivity(){
        uiDevice.findObject(By.res(packageName, TO_DETAILS_ACTIVITY_BUTTON)).click()
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT_LONG
        )
        assertEquals(totalCountTextView.text.toString(), String.format(
            Locale.getDefault(),
            totalCount,
           0
        ))
    }

    @Test
    fun mainActivity_RotateScreen() {
        uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT)).text = SEARCH_TEXT
        uiDevice.findObject(By.res(packageName, SEARCH_FAB)).click()
        val totalCountTextView = uiDevice.wait(
            Until.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW)), TIMEOUT_LONG
        )
        val totalCount = totalCountTextView.text.toString()
        uiDevice.setOrientationLeft()
        if (isKeyboardOpened())
            uiDevice.pressBack()
        uiDevice.waitForIdle(TIMEOUT_SHOT)
        val searchEditTextRotate = uiDevice.findObject(By.res(packageName, SEARCH_EDIT_TEXT))
        val totalCountTextViewRotate = uiDevice.findObject(By.res(packageName, TOTAL_COUNT_TEXT_VIEW))
        assertEquals(searchEditTextRotate.text.toString(), SEARCH_TEXT)
        assertEquals(totalCountTextViewRotate.text.toString(), totalCount)
    }

    private fun isKeyboardOpened():Boolean{
        val automation = getInstrumentation().uiAutomation
        for (window in automation.windows) {
            if (window.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
                return true
            }
        }
        return false
    }

    companion object {
        private const val TIMEOUT_LONG = 5000L
        private const val TIMEOUT_SHOT = 500L
        private const val PROGRESS_BAR = "progressBar"
        private const val TOTAL_COUNT_TEXT_VIEW = "totalCountTextView"
        private const val SEARCH_EDIT_TEXT = "searchEditText"
        private const val TO_DETAILS_ACTIVITY_BUTTON = "toDetailsActivityButton"
        private const val SEARCH_FAB = "searchFloatingActionButton"
        private const val SEARCH_TEXT = "Algol"
    }

}