package com.example.searchgithub.view.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.searchgithub.R
import com.example.searchgithub.presenter.details.DetailsPresenter
import com.example.searchgithub.presenter.details.PresenterDetailsContract
import java.util.*

class DetailsActivity : AppCompatActivity(), ViewDetailsContract {

    private lateinit var presenter: PresenterDetailsContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setUI()
        presenter = extractPresenter()
        presenter.onAttach(this)
    }

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): PresenterDetailsContract {
        return presenter
    }

    @Suppress("DEPRECATION")
    private fun extractPresenter(): PresenterDetailsContract =
        lastCustomNonConfigurationInstance as? PresenterDetailsContract ?: DetailsPresenter(
            intent.getIntExtra(TOTAL_COUNT_EXTRA, 0)
        )

    private fun setUI() {
        findViewById<Button>(R.id.decrementButton).setOnClickListener { presenter.onDecrement() }
        findViewById<Button>(R.id.incrementButton).setOnClickListener { presenter.onIncrement() }
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    private fun setCountText(count: Int) {
        findViewById<TextView>(R.id.totalCountTextView).text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
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
