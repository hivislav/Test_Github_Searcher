package com.hivislav.testgithubsearcher.di

import android.app.Application
import android.app.DownloadManager
import android.content.Context
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

        @Provides
        fun provideDownloadManager(application: Application): DownloadManager {
            return application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        }
    }
}