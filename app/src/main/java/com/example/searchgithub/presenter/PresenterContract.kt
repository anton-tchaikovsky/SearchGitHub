package com.example.searchgithub.presenter

import com.example.searchgithub.view.ViewContract

interface PresenterContract <T: ViewContract> {
    fun onAttach(view:T)

    fun onDetach()
}
