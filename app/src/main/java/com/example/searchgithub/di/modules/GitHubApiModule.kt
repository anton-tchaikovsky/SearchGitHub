package com.example.searchgithub.di.modules

import com.example.searchgithub.repository.GitHubApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GitHubApiModule {

    @Singleton
    @Provides
    fun gitHubApi(): GitHubApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(GitHubApi::class.java)

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

}