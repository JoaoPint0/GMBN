package com.endeavour.feed.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.endeavour.core.vo.Resource
import com.endeavour.core.vo.Status
import com.endeavour.core.vo.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel internal constructor(private val repository: com.endeavour.domain.repo.GMBNRepository) : ViewModel() {

    private val videoIds =
        MutableLiveData<Resource<List<String>>>().apply { Resource.loading(emptyList<Video>()) }

    init {
        fetchIds()
    }

    val videos = videoIds.switchMap { ids ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            when (ids.status) {
                Status.SUCCESS -> {
                    if (ids.data.isNullOrEmpty()) {
                        emit(Resource.success(repository.loadCachedVideos()))
                    } else {
                        emitSource(repository.searchVideos(ids.data as List<String>))
                    }
                }
                Status.CONNECTION -> emit(Resource.connection(repository.loadCachedVideos()))
                Status.ERROR -> emit(
                    Resource.error(
                        ids.message ?: "",
                        repository.loadCachedVideos()
                    )
                )
                Status.LOADING -> emit(Resource.loading(emptyList<Video>()))
            }
        }
    }

    val viewStatus = videos.switchMap { videos ->
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            Log.e("Viewmodel", videos.status.name)
            val result = videos.data.isNullOrEmpty()
            emit(
                when (videos.status) {
                    Status.SUCCESS -> MainViewStatus(true)
                    Status.LOADING -> MainViewStatus(loading = true)
                    Status.CONNECTION -> MainViewStatus(
                        !result,
                        error = result,
                        message = "Verify you Internet Connection"
                    )
                    Status.ERROR -> MainViewStatus(
                        !result,
                        error = result,
                        message = "Unexpected Error, please Retry"
                    )
                }
            )
        }
    }

    fun fetchIds() {
        viewModelScope.launch(Dispatchers.IO) { videoIds.postValue(repository.getVideoIds()) }
    }

    data class MainViewStatus(
        val success: Boolean = false,
        val loading: Boolean = false,
        val error: Boolean = false,
        val message: String = ""
    )

    override fun onCleared() {
        GlobalScope.launch(Dispatchers.IO) { repository.clearTable() }
        super.onCleared()
    }
}
