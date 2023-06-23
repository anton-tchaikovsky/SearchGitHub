package com.example.searchgithub.presenter.details

import com.example.searchgithub.presenter.PresenterContract
import com.example.searchgithub.view.details.ViewDetailsContract

interface PresenterDetailsContract : PresenterContract<ViewDetailsContract> {
    fun onIncrement()
    fun onDecrement()
}
