package com.hivislav.testgithubsearcher.data.repository

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.hivislav.testgithubsearcher.data.database.RepoDao
import com.hivislav.testgithubsearcher.data.mapper.RepoMapper
import com.hivislav.testgithubsearcher.data.network.ApiService
import com.hivislav.testgithubsearcher.domain.GithubRepository
import com.hivislav.testgithubsearcher.domain.Repo
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val downloadManager: DownloadManager,
    private val application: Application,
    private val apiService: ApiService,
    private val repoDao: RepoDao,
    private val mapper: RepoMapper
) : GithubRepository {

    override suspend fun loadRepos(userName: String): List<Pair<Repo, Boolean>> {
        return apiService.loadRepos(userName).map {
            mapper.mapDtoToPairEntity(it)
        }
    }

    override suspend fun getDownloadRepos(): List<Pair<Repo, Boolean>> {
        return repoDao.getReposList().map {
            mapper.mapDbModelToPairEntity(it)
        }
    }

    override suspend fun downloadRepo(repo: Repo) {
        val url = mapper.mapUrlRepo(repo.archiveUrl)

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(repo.repoName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "${repo.repoName}$ZIP_FORMAT"
            )
        downloadManager.enqueue(request)

        val uriPathExternalDir = "${Environment.DIRECTORY_DOWNLOADS}/${repo.repoName}$ZIP_FORMAT"

        repoDao.addRepo(
            mapper.mapEntityToDbModel(repo, uriPathExternalDir)
        )
    }

    companion object {
        private const val ZIP_FORMAT = ".zip"
    }
}