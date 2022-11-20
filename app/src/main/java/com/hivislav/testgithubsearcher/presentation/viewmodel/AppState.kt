package com.hivislav.testgithubsearcher.presentation.viewmodel

import com.hivislav.testgithubsearcher.domain.Repo

sealed class AppState {
    data class Success(val reposList: List<Pair<Repo, Boolean>>): AppState()
    data class Error(val errorMessage: String): AppState()
    object Loading: AppState()
}
