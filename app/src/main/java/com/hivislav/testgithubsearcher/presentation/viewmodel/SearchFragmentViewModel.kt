package com.hivislav.testgithubsearcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivislav.testgithubsearcher.domain.DownloadRepoUseCase
import com.hivislav.testgithubsearcher.domain.GetUrlRepoUseCase
import com.hivislav.testgithubsearcher.domain.LoadReposUseCase
import com.hivislav.testgithubsearcher.domain.Repo
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchFragmentViewModel @Inject constructor(
    private val loadReposUseCase: LoadReposUseCase,
    private val getUrlRepoUseCase: GetUrlRepoUseCase,
    private val downloadRepoUseCase: DownloadRepoUseCase
) : ViewModel() {

    private val _loadedRepos = MutableLiveData<SearchFragmentAppState>()
    val loadedRepos: LiveData<SearchFragmentAppState>
        get() = _loadedRepos

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _loadedRepos.postValue(SearchFragmentAppState.Error(throwable.message.toString()))
    }

    fun loadRepos(userName: String) {
        _loadedRepos.postValue(SearchFragmentAppState.Loading)
        viewModelScope.launch(Dispatchers.Main + coroutineExceptionHandler) {
            val deferredRepoList = async(Dispatchers.IO) {
                loadReposUseCase(userName)
            }
            val listRepos = deferredRepoList.await()
            if (isActive) {
                _loadedRepos.postValue(SearchFragmentAppState.Success(listRepos))
            }
        }
    }

    fun downloadRepo(repo: Repo) {
        downloadRepoUseCase(repo)
    }
}