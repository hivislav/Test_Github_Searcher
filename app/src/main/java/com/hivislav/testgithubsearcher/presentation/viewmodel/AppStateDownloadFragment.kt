package com.hivislav.testgithubsearcher.presentation.viewmodel

import com.hivislav.testgithubsearcher.domain.Repo

sealed class AppStateDownloadFragment {
    data class Success(val reposList: List<Pair<Repo, Boolean>>): AppStateDownloadFragment()
    data class Error(val errorMessage: String): AppStateDownloadFragment()
}
