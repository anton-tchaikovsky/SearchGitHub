package com.example.searchgithub.model

sealed class AppState {

    class Success(val SearchResponse: SearchResponse): AppState()
    class Error (val Error: Throwable): AppState()
    object Loading: AppState()


}