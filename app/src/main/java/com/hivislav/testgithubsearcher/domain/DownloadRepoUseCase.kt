package com.hivislav.testgithubsearcher.domain

import javax.inject.Inject

class DownloadRepoUseCase @Inject constructor(
    private val repository: GithubRepository
) {
    operator fun invoke(repo: Repo) = repository.downloadRepo(repo)
}