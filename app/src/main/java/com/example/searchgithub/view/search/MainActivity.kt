package com.example.searchgithub.view.search

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.searchgithub.App
import com.example.searchgithub.R
import com.example.searchgithub.model.SearchResult
import com.example.searchgithub.presenter.search.PresenterSearchContract
import com.example.searchgithub.view.details.DetailsActivity
import java.util.Locale
import javax.inject.Inject

class MainActivity : AppCompatActivity(), ViewSearchContract {

    private val searchResultAdapter = SearchResultAdapter()
    @Inject
    lateinit var presenter: PresenterSearchContract
    private var totalCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance.appComponent.inject(this)
        setContentView(R.layout.activity_main)
        setUI()
        presenter = extractPresenter()
        presenter.onAttach(this)
    }

    @Suppress("DEPRECATION")
    private fun extractPresenter(): PresenterSearchContract =
        lastCustomNonConfigurationInstance as? PresenterSearchContract ?: presenter

    @Deprecated("Deprecated in Java")
    override fun onRetainCustomNonConfigurationInstance(): PresenterSearchContract {
        return presenter
    }

    private fun setUI() {
        findViewById<Button>(R.id.toDetailsActivityButton).setOnClickListener {
            startActivity(DetailsActivity.getIntent(this, totalCount))
        }
        setQueryListener()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            setHasFixedSize(true)
            adapter = searchResultAdapter
        }
    }

    private fun setQueryListener() {
        findViewById<EditText>(R.id.searchEditText).setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = findViewById<EditText>(R.id.searchEditText).text.toString()
                if (query.isNotBlank()) {
                    presenter.searchGitHub(query)
                    return@OnEditorActionListener true
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.enter_search_word),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@OnEditorActionListener false
                }
            }
            false
        })
    }

    override fun displaySearchResults(
        searchResults: List<SearchResult>,
        totalCount: Int
    ) {
        findViewById<TextView>(R.id.totalCountTextView).apply {
            visibility = View.VISIBLE
            text = String.format(
                Locale.getDefault(), getString(R.string.results_count),
                totalCount)
        }
        this.totalCount = totalCount
        searchResultAdapter.updateResults(searchResults)
    }

    override fun displayError() {
        Toast.makeText(this, getString(R.string.undefined_error), Toast.LENGTH_SHORT).show()
    }

    override fun displayError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }

    override fun displayLoading(show: Boolean) {
        findViewById<ProgressBar>(R.id.progressBar).apply {
            visibility = if (show) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }


}
