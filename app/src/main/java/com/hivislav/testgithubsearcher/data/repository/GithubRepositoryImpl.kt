package com.hivislav.testgithubsearcher.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.hivislav.testgithubsearcher.data.mapper.RepoMapper
import com.hivislav.testgithubsearcher.data.network.ApiService
import com.hivislav.testgithubsearcher.domain.GithubRepository
import com.hivislav.testgithubsearcher.domain.Repo
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: RepoMapper
): GithubRepository {

    override suspend fun loadRepos(userName: String): List<Repo> {
        return apiService.loadRepos(userName).map {
            mapper.mapDtoToEntity(it)
        }
    }

    override fun getUrlRepo(repo: Repo): String {
        return repo.urlRepository
    }

    override fun downloadRepo() {
        // TODO
    }
}