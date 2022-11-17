package com.hivislav.testgithubsearcher.domain

import androidx.lifecycle.LiveData

interface GithubRepository {

    fun loadRepos(): LiveData<List<Repo>>

    fun getUrlRepo(repoId: Long): String

    fun downloadRepo()
}