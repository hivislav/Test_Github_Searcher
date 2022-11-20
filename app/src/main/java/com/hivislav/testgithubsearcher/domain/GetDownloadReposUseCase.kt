package com.hivislav.testgithubsearcher.domain

import javax.inject.Inject

class GetDownloadReposUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    suspend operator fun invoke() = repository.getDownloadRepos()
}