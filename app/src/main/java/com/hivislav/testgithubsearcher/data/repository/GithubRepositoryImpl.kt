package com.hivislav.testgithubsearcher.data.repository

import android.app.Application
import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import com.hivislav.testgithubsearcher.data.mapper.RepoMapper
import com.hivislav.testgithubsearcher.data.network.ApiService
import com.hivislav.testgithubsearcher.domain.GithubRepository
import com.hivislav.testgithubsearcher.domain.Repo
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val downloadManager: DownloadManager,
    private val application: Application,
    private val apiService: ApiService,
    private val mapper: RepoMapper
) : GithubRepository {

    override suspend fun loadRepos(userName: String): List<Pair<Repo, Boolean>> {
        return apiService.loadRepos(userName).map {
            mapper.mapDtoToPairEntity(it)
        }
    }

    override fun getUrlRepo(repo: Repo): String {
        return repo.urlRepository
    }

    override fun downloadRepo(repo: Repo) {
        val url = mapper.mapUrlRepo(repo.archiveUrl)

        val path = Environment.getExternalStoragePublicDirectory(
            "${Environment.DIRECTORY_DOWNLOADS}/${repo.repoName}$ZIP_FORMAT"
        )

        val request = DownloadManager.Request(Uri.parse(url))
            .setTitle(repo.repoName)
            .setDescription("Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "${repo.repoName}$ZIP_FORMAT"
            )
        downloadManager.enqueue(request)
    }

    companion object {
        private const val ZIP_FORMAT = ".zip"
    }
}