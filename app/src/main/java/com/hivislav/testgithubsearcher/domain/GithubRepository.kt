package com.hivislav.testgithubsearcher.domain


interface GithubRepository {

    suspend fun loadRepos(userName: String): List<Pair<Repo, Boolean>>

    suspend fun getDownloadRepos(): List<Pair<Repo, Boolean>>

    suspend fun downloadRepo(repo: Repo)
}