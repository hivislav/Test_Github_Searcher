package com.hivislav.testgithubsearcher.di

import android.app.Application
import com.hivislav.testgithubsearcher.presentation.ui.DownloadFragment
import com.hivislav.testgithubsearcher.presentation.ui.SearchFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: SearchFragment)

    fun inject(fragment: DownloadFragment)

    @Component.Factory
    interface ApplicationComponentFactory{

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}