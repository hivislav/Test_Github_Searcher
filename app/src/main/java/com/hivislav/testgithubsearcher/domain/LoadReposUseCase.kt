package com.hivislav.testgithubsearcher.domain

import javax.inject.Inject

class LoadReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke(userName: String) = repository.loadRepos(userName)
}