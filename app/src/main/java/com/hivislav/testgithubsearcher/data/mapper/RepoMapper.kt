package com.hivislav.testgithubsearcher.data.mapper

import com.hivislav.testgithubsearcher.data.database.RepoDbModel
import com.hivislav.testgithubsearcher.data.network.model.RepoDto
import com.hivislav.testgithubsearcher.domain.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun mapDtoToPairEntity(dto: RepoDto): Pair<Repo, Boolean> {
        return Pair(
            first = Repo(
                dto.id,
                dto.repoName,
                dto.owner.login,
                dto.owner.avatarUrl,
                dto.htmlUrl,
                dto.archiveUrl,
                Repo.REMOTE_VIEW_TYPE
            ), second = false
        )
    }

    fun mapDbModelToPairEntity(repoDbModel: RepoDbModel): Pair<Repo, Boolean> {
        return Pair(
            first = Repo(
                repoDbModel.repoId,
                repoDbModel.repoName,
                repoDbModel.userName,
                repoDbModel.urlUserAvatar,
                repoDbModel.urlRepository,
                repoDbModel.uriPathExternalDir,
                Repo.LOCAL_VIEW_TYPE
            ), second = false
        )
    }

    fun mapEntityToDbModel(repo: Repo, uriPathExternalDir: String): RepoDbModel {
        return RepoDbModel(
            repo.repoId,
            repo.repoName,
            repo.userName,
            repo.urlUserAvatar,
            repo.urlRepository,
            uriPathExternalDir
        )
    }


    fun mapUrlRepo(repoUrl: String): String {
        return repoUrl.replace(ARCHIVE_FORMAT_END_POINT, ZIP_FORMAT_FOR_REQUEST, true)
    }

    companion object {
        private const val ARCHIVE_FORMAT_END_POINT = "{archive_format}{/ref}"
        private const val ZIP_FORMAT_FOR_REQUEST = "zipball"
    }
}