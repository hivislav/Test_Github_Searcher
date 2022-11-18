package com.hivislav.testgithubsearcher.domain

import androidx.lifecycle.LiveData

interface GithubRepository {

    suspend fun loadRepos(userName: String): LiveData<List<Repo>>

    fun getUrlRepo(repo: Repo): String

    fun downloadRepo()
}