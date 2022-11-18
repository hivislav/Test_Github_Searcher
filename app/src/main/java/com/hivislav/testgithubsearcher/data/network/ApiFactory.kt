package com.hivislav.testgithubsearcher.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ApiFactory {

    private const val GITHUB_BASE_URL = "https://api.github.com/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(GITHUB_BASE_URL)
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}