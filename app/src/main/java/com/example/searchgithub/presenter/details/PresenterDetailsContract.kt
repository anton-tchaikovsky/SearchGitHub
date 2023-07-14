package com.example.searchgithub.presenter.details

import com.example.searchgithub.presenter.PresenterContract
import com.example.searchgithub.view.details.ViewDetailsContract

interface PresenterDetailsContract : PresenterContract<ViewDetailsContract> {

    var count: Int
    fun onIncrement()
    fun onDecrement()
}
