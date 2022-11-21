package com.hivislav.testgithubsearcher.presentation.viewmodel

import com.hivislav.testgithubsearcher.domain.Repo

sealed class AppStateSearchFragment {
    data class Success(val reposList: List<Pair<Repo, Boolean>>): AppStateSearchFragment()
    data class Error(val errorMessage: String): AppStateSearchFragment()
    object Loading: AppStateSearchFragment()
}
