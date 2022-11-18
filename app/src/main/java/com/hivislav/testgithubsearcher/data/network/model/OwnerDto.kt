package com.hivislav.testgithubsearcher.data.network.model

import com.google.gson.annotations.SerializedName

data class OwnerDto(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
