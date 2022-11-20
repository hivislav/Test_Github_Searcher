package com.hivislav.testgithubsearcher.domain

interface GithubRepository {

    suspend fun loadRepos(userName: String): List<Pair<Repo, Boolean>>

    fun getUrlRepo(repo: Repo): String

    fun downloadRepo(repo: Repo)
}