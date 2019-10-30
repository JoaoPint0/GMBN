package com.endeavour.domain.repo


import androidx.lifecycle.LiveData
import com.endeavour.core.utils.NetworkBoundResource
import com.endeavour.core.vo.Resource
import com.endeavour.core.vo.Video
import com.endeavour.domain.db.VideoDao
import com.endeavour.domain.model.CommentResponse
import com.endeavour.domain.model.VideoDetailedResponse

class GMBNRepository private constructor(
    private val youtubeService: com.endeavour.domain.api.YoutubeService,
    private val videoDao: VideoDao
) {

    private var nextPageToken = ""

    suspend fun getVideoIds(): Resource<List<String>> {
        return try {
            val videos = if (nextPageToken.isBlank()){
                youtubeService.videoIds(
                    KEY,
                    CHANNEL_ID,
                    SNIPPET,
                    ORDER,
                    MAX_RESULTS
                )
            }else {
                youtubeService.nextVideoIds(
                    KEY,
                    CHANNEL_ID, nextPageToken,
                    SNIPPET,
                    ORDER,
                    MAX_RESULTS
                )
            }
            nextPageToken = videos.body()?.nextPageToken ?: ""
            if (videos.isSuccessful) {
                Resource.success(videos.body()?.items?.map { it.toId() } ?: emptyList())
            } else {
                Resource.error(videos.code().toString(), emptyList())
            }
        } catch (ex: Exception) {
            Resource.connection(emptyList())
        }

    }

    fun searchVideos(ids: List<String>): LiveData<Resource<List<Video>>> {
        return object : NetworkBoundResource<List<Video>, VideoDetailedResponse>() {
            override suspend fun saveCallResult(item: VideoDetailedResponse) =
                videoDao.insertAll(item.items.map { it.toEntity() })

            override fun loadFromDb() = videoDao.getVideos()

            override suspend fun createCall() =
                youtubeService.detailedVideos(
                    KEY, ids.joinToString(),
                    FULL_DETAILS,
                    ORDER
                )

        }.asLiveData()
    }

    fun loadVideoById(videoId: String) = videoDao.loadVideoById(videoId)

    fun loadCachedVideos() = videoDao.loadCachedVideos()

    suspend fun loadVideoComments(videoId: String): Resource<CommentResponse> {

        return try {
            val comments = youtubeService.videoComments(
                KEY, videoId,
                SNIPPET
            )
            if (comments.isSuccessful) {
                Resource.success(comments.body())
            } else {
                Resource.error(comments.message(), CommentResponse(emptyList()))
            }
        } catch (ex: Exception) {
            Resource.connection(CommentResponse(emptyList()))
        }
    }

    fun clearTable() {
        videoDao.nukeTable()
    }

    companion object {
        val KEY = "AIzaSyAr-AUxFqEp82k4pYRSsvDfs_GPmmLeeGg"
        val CHANNEL_ID = "UC_A--fhX5gea0i4UtpD99Gg"
        val SNIPPET = "snippet"
        val FULL_DETAILS = "snippet,contentDetails,statistics"
        val ORDER = "date"
        val MAX_RESULTS = 50

        @Volatile
        private var instance: GMBNRepository? = null

        fun getInstance(youtubeService: com.endeavour.domain.api.YoutubeService, videoDao: VideoDao) =
            instance ?: synchronized(this) {
                instance ?: GMBNRepository(
                    youtubeService,
                    videoDao
                ).also { instance = it }
            }
    }
}