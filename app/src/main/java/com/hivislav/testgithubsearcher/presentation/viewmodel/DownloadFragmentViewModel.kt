package com.hivislav.testgithubsearcher.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hivislav.testgithubsearcher.domain.GetDownloadReposUseCase
import kotlinx.coroutines.*
import javax.inject.Inject

class DownloadFragmentViewModel @Inject constructor(
    private val getDownloadReposUseCase: GetDownloadReposUseCase
) : ViewModel() {

    private val _downloadedRepos = MutableLiveData<AppState>()
    val downloadedRepos: LiveData<AppState>
        get() = _downloadedRepos

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _downloadedRepos.postValue(AppState.Error(throwable.message.toString()))
    }

    fun getDownloadRepos() {
        _downloadedRepos.postValue(AppState.Loading)
        viewModelScope.launch ( Dispatchers.Main + coroutineExceptionHandler ) {
            val deferredRepoList = async(Dispatchers.IO) {
                getDownloadReposUseCase()
            }
            val repoList = deferredRepoList.await()
            if (isActive) {
                _downloadedRepos.postValue(AppState.Success(repoList))
            }
        }
    }
}