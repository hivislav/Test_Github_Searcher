package com.hivislav.testgithubsearcher.data.mapper

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
                dto.archiveUrl
            ), second = false
        )
    }

    fun mapUrlRepo(repoUrl: String): String {
        return repoUrl.replace("{archive_format}{/ref}", "zipball", true)
    }
}