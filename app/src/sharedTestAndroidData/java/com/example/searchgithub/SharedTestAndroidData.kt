package com.example.searchgithub

import android.view.View
import android.view.accessibility.AccessibilityWindowInfo
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matcher

internal const val SEARCH_TEXT = "Algol"
internal const val TOTAL_COUNT_TEST = 3808
internal const val TOTAL_COUNT_INCREMENT = 1
internal const val TOTAL_COUNT_DECREMENT = -1
internal const val TOTAL_COUNT_ZERO = 0
internal const val TOTAL_COUNT_TEXT_TEST = "Number of results: $TOTAL_COUNT_TEST"
internal const val TOTAL_COUNT_TEXT_INCREMENT = "Number of results: $TOTAL_COUNT_INCREMENT"
internal const val TOTAL_COUNT_TEXT_DECREMENT = "Number of results: $TOTAL_COUNT_DECREMENT"
internal const val TOTAL_COUNT_TEXT_ZERO = "Number of results: $TOTAL_COUNT_ZERO"
internal const val SEARCH_EMPTY_TEXT = ""
internal const val SEARCH_BLANK_TEXT = " "
internal const val RECYCLE_VIEW_POSITION_TEST = 25
internal const val RECYCLE_VIEW_FULL_NAME_FAKE = "FullName: 20"

internal const val DELAY_LONG = 5000L
internal const val DELAY_SHOT = 500L
internal const val SEARCH_WORD = "Search word"
internal const val INCREMENT = "+"
internal const val DECREMENT = "-"


internal fun delay(delay: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
        override fun getDescription(): String = "wait for $3 seconds"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}

internal fun isKeyboardOpened():Boolean{
    val automation = InstrumentationRegistry.getInstrumentation().uiAutomation
    for (window in automation.windows) {
        if (window.type == AccessibilityWindowInfo.TYPE_INPUT_METHOD) {
            return true
        }
    }
    return false
}



