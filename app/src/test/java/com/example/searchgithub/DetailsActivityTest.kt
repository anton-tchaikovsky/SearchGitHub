package com.example.searchgithub

import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchgithub.view.details.DetailsActivity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.N])
class DetailsActivityTest {

    @Test
    fun activityCreateIntent() {
        val context: Context = ApplicationProvider.getApplicationContext()
        val intent = DetailsActivity.getIntent(context, TOTAL_COUNT_TEST)
        val bundle = intent.extras
        assertNotNull(intent)
        assertNotNull(bundle)
        assertEquals(
            bundle?.getInt(DetailsActivity.TOTAL_COUNT_EXTRA, TOTAL_COUNT_ZERO),
            TOTAL_COUNT_TEST
        )
    }

}