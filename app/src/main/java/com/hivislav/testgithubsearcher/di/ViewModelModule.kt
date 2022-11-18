package com.hivislav.testgithubsearcher.di

import androidx.lifecycle.ViewModel
import com.hivislav.testgithubsearcher.presentation.viewmodel.SearchFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchFragmentViewModel::class)
    fun bindCoinViewModel(viewModel: SearchFragmentViewModel): ViewModel
}