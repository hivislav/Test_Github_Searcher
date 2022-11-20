package com.hivislav.testgithubsearcher.presentation.viewmodel

import com.hivislav.testgithubsearcher.domain.Repo

sealed class SearchFragmentAppState {
    data class Success(val reposList: List<Pair<Repo, Boolean>>): SearchFragmentAppState()
    data class Error(val errorMessage: String): SearchFragmentAppState()
    object Loading: SearchFragmentAppState()
}
