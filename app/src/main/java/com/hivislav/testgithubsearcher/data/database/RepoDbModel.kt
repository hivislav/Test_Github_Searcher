package com.hivislav.testgithubsearcher.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repositories")
data class RepoDbModel(
    @PrimaryKey
    val repoId: Long,
    val repoName: String,
    val userName: String,
    val urlUserAvatar: String,
    val urlRepository: String,
    val uriPathExternalDir: String,
)
