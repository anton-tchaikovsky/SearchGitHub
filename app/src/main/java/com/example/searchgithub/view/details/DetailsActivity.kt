package com.example.searchgithub.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchgithub.R

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        if (savedInstanceState == null)
            openDetailsFragment(intent.getIntExtra(TOTAL_COUNT_EXTRA, 0))
    }

    private fun openDetailsFragment(count: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.details_fragment_container, DetailsFragment.getInstance(count))
            .commitAllowingStateLoss()
    }

    companion object {
        const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        fun getIntent(context: Context, totalCount: Int): Intent {
            return Intent(context, DetailsActivity::class.java).apply {
                putExtra(TOTAL_COUNT_EXTRA, totalCount)
            }
        }
    }
}
