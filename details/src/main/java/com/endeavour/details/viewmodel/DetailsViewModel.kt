package com.endeavour.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.endeavour.core.vo.Resource
import com.endeavour.core.vo.Status
import kotlinx.coroutines.Dispatchers

class DetailsViewModel internal constructor(repository: com.endeavour.domain.repo.GMBNRepository, videoId: String) :
    ViewModel() {

    val showDescription = MutableLiveData<Boolean>().apply { value = false }

    val video = repository.loadVideoById(videoId)

    val comments = liveData(Dispatchers.IO) {
        val commentResponse = repository.loadVideoComments(videoId)
        if(commentResponse.status == Status.SUCCESS){
            emit(Resource.success(commentResponse.data?.items?.map { it.toEntity() }))
        }else {
            emit(Resource.error(commentResponse.message ?: "", emptyList()))
        }
    }

    fun updateLengthDescription() {
        val show = showDescription.value ?: false
        showDescription.value = !show
    }
}
