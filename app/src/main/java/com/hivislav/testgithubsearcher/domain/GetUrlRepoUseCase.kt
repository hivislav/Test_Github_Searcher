package com.hivislav.testgithubsearcher.domain

import javax.inject.Inject

class GetUrlRepoUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke(repoId: Long) = repository.getUrlRepo(repoId)
}