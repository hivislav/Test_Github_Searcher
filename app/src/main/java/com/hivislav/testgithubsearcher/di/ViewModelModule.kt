package com.hivislav.testgithubsearcher.di

import androidx.lifecycle.ViewModel
import com.hivislav.testgithubsearcher.presentation.viewmodel.DownloadFragmentViewModel
import com.hivislav.testgithubsearcher.presentation.viewmodel.SearchFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel::class)
    fun bindSearchViewModel(viewModel: SearchFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloadFragmentViewModel::class)
    fun bindDownloadViewModel(viewModel: DownloadFragmentViewModel): ViewModel
}