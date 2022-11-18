package com.hivislav.testgithubsearcher.data.mapper

import com.hivislav.testgithubsearcher.data.network.model.RepoDto
import com.hivislav.testgithubsearcher.domain.Repo
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun mapDtoToEntity(dto: RepoDto): Repo {
        return Repo(
            dto.id,
            dto.repoName,
            dto.owner.login,
            dto.owner.avatarUrl,
            dto.htmlUrl
        )
    }
}