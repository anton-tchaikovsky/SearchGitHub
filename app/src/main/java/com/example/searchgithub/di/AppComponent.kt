package com.example.searchgithub.di

import android.content.Context
import com.example.searchgithub.App
import com.example.searchgithub.di.modules.GitHubApiModule
import com.example.searchgithub.di.modules.PresenterModule
import com.example.searchgithub.di.modules.RepositoryModule
import com.example.searchgithub.di.modules.SchedulerProviderModule
import com.example.searchgithub.di.modules.ViewModelModule
import com.example.searchgithub.view.details.DetailsFragment
import com.example.searchgithub.view.search.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [RepositoryModule::class,
        PresenterModule::class,
        GitHubApiModule::class,
        SchedulerProviderModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(applicationContext: Context): Builder

        fun build(): AppComponent

    }

    fun inject(app: App)
    fun inject(mainActivity: MainActivity)
    fun inject(detailsFragment: DetailsFragment)

}