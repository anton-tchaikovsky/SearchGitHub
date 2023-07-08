package com.example.searchgithub.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.searchgithub.App
import com.example.searchgithub.R
import com.example.searchgithub.presenter.details.PresenterDetailsContract
import java.util.Locale
import javax.inject.Inject

class DetailsFragment: Fragment(), ViewDetailsContract {

    @Inject
    lateinit var presenter: PresenterDetailsContract

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        App.instance.appComponent.inject(this)
        arguments?.let {
            presenter.count = it.getInt(TOTAL_COUNT_EXTRA, 0)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUI()
        presenter.onAttach(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.onDetach()
    }

    override fun setCount(count: Int) {
        setCountText(count)
    }

    private fun setCountText(count: Int) {
        view?.findViewById<TextView>(R.id.totalCountTextView)?.text =
            String.format(Locale.getDefault(), getString(R.string.results_count), count)
    }

    private fun setUI() {
        view?.findViewById<Button>(R.id.decrementButton)?.setOnClickListener { presenter.onDecrement() }
        view?.findViewById<Button>(R.id.incrementButton)?.setOnClickListener { presenter.onIncrement() }
    }

    companion object{
        internal const val TOTAL_COUNT_EXTRA = "TOTAL_COUNT_EXTRA"

        @JvmStatic
        fun getInstance(count: Int) =
            DetailsFragment().apply {
                arguments = bundleOf(Pair(TOTAL_COUNT_EXTRA, count))
        }
    }

}