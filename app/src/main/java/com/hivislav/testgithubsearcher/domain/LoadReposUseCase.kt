package com.hivislav.testgithubsearcher.domain

import javax.inject.Inject

class LoadReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke() = repository.loadRepos()
}