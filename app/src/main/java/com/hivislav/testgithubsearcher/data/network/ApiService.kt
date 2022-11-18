package com.hivislav.testgithubsearcher.data.network

import androidx.lifecycle.LiveData
import com.hivislav.testgithubsearcher.data.network.model.RepoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET(GET_REPOS_LIST_FROM_USER)
    suspend fun loadRepos(@Path("username") userName: String ): LiveData<List<RepoDto>>

    companion object {
        private const val GET_REPOS_LIST_FROM_USER = "users/{username}/repos"
    }
}
