package com.hivislav.testgithubsearcher.domain

interface GithubRepository {

    suspend fun loadRepos(userName: String): List<Repo>

    fun getUrlRepo(repo: Repo): String

    fun downloadRepo()
}