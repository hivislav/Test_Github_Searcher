package com.hivislav.testgithubsearcher.domain

data class Repo(
    val repoId: Long,
    val repoName: String,
    val userName: String,
    val urlUserAvatar: String,
    val urlRepository: String
)
