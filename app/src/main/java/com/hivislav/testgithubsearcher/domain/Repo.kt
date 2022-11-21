package com.hivislav.testgithubsearcher.domain

data class Repo(
    val repoId: Long,
    val repoName: String,
    val userName: String,
    val urlUserAvatar: String,
    val urlRepository: String,
    val archiveUrl: String,
    val itemViewType: Int
) {
    companion object {
        const val REMOTE_VIEW_TYPE = 11
        const val LOCAL_VIEW_TYPE = 22
    }
}
