package com.example.searchgithub

import android.app.Application
import com.example.searchgithub.di.AppComponent
import com.example.searchgithub.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = createAppComponent()
        appComponent.inject(this)
    }

    private fun createAppComponent() =
        DaggerAppComponent.builder()
            .applicationContext(this)
            .build()


    companion object {
        lateinit var instance: App
    }
}