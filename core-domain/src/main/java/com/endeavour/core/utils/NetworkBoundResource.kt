package com.endeavour.core.utils

import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.endeavour.core.vo.Resource
import retrofit2.Response

abstract class NetworkBoundResource<ResultType, RequestType> {

    private val result = liveData<Resource<ResultType>> {

        val disposable = emitSource(loadFromDb().map { Resource.loading(it) })
        try {
            val response = createCall()
            disposable.dispose()
            if (response.isSuccessful) {
                saveCallResult(response.body()!!)
                emitSource(loadFromDb().map { Resource.success(it) })
            } else {
                onFetchFailed(response.toString())
                emitSource(loadFromDb().map { Resource.error(response.message(), it) })
            }
        } catch (exception: Exception) {
            onFetchFailed(exception.toString())
            emitSource(loadFromDb().map { Resource.connection(it) })
        }
    }

    protected open fun onFetchFailed(message: String) {
        Log.e("NetworkResourse", message)
    }

    fun asLiveData() = result

    @WorkerThread
    protected abstract suspend fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    @MainThread
    protected abstract suspend fun createCall(): Response<RequestType>
}
