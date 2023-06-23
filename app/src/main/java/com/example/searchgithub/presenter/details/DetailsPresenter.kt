package com.example.searchgithub.presenter.details

import com.example.searchgithub.view.details.ViewDetailsContract

internal class DetailsPresenter internal constructor(
    internal var count: Int = 0
) : PresenterDetailsContract {

    internal var viewContract: ViewDetailsContract? = null

    override fun onIncrement() {
        count++
        viewContract?.setCount(count)
    }

    override fun onDecrement() {
        count--
        viewContract?.setCount(count)
    }

    override fun onAttach(view: ViewDetailsContract) {
        viewContract = view
        viewContract?.setCount(count)
    }

    override fun onDetach() {
        viewContract = null
    }
}
