package com.hivislav.testgithubsearcher.di

import com.hivislav.testgithubsearcher.data.network.ApiFactory
import com.hivislav.testgithubsearcher.data.network.ApiService
import com.hivislav.testgithubsearcher.data.repository.GithubRepositoryImpl
import com.hivislav.testgithubsearcher.domain.GithubRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindGithubRepository(impl: GithubRepositoryImpl): GithubRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}