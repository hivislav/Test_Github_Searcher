package com.hivislav.testgithubsearcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivislav.testgithubsearcher.domain.DownloadRepoUseCase
import com.hivislav.testgithubsearcher.domain.LoadReposUseCase
import com.hivislav.testgithubsearcher.domain.Repo
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val loadReposUseCase: LoadReposUseCase,
    private val downloadRepoUseCase: DownloadRepoUseCase
) : ViewModel() {

    private val _loadedRepos = MutableLiveData<AppStateSearchFragment>()
    val loadedRepos: LiveData<AppStateSearchFragment>
        get() = _loadedRepos

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadedRepos.postValue(AppStateSearchFragment.Error(throwable.message.toString()))
    }

    fun loadRepos(userName: String) {
        _loadedRepos.postValue(AppStateSearchFragment.Loading)
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            val deferredRepoList = async(Dispatchers.IO) {
                loadReposUseCase(userName)
            }
            val listRepos = deferredRepoList.await()
            if (isActive) {
                _loadedRepos.postValue(AppStateSearchFragment.Success(listRepos))
            }
        }
    }

    fun downloadRepo(repo: Repo) {
        viewModelScope.launch {
            downloadRepoUseCase(repo)
        }
    }
}