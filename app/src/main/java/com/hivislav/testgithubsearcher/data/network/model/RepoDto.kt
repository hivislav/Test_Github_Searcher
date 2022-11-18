package com.hivislav.testgithubsearcher.data.network.model

import com.google.gson.annotations.SerializedName

data class RepoDto(
    val id: Long,
    @SerializedName("name")
    val repoName: String,
    val owner: OwnerDto,
    @SerializedName("html_url")
    val htmlUrl: String
)
