package com.endeavour.domain.api

import com.endeavour.domain.model.CommentResponse
import com.endeavour.domain.model.VideoDetailedResponse
import com.endeavour.domain.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeService {

    @GET("youtube/v3/search")
    suspend fun videoIds(
        @Query("key") key: String,
        @Query("channelId") channelId: String,
        @Query("part") part: String,
        @Query("order") order: String,
        @Query("maxResults") maxResults: Int
    ): Response<VideoResponse>

    @GET("youtube/v3/search")
    suspend fun nextVideoIds(
        @Query("key") key: String,
        @Query("channelId") channelId: String,
        @Query("pageToken") token: String,
        @Query("part") part: String,
        @Query("order") order: String,
        @Query("maxResults") maxResults: Int
    ): Response<VideoResponse>

    @GET("youtube/v3/videos")
    suspend fun detailedVideos(
        @Query("key") key: String,
        @Query("id") id: String,
        @Query("part") part: String,
        @Query("order") order: String
    ): Response<VideoDetailedResponse>

    @GET("youtube/v3/commentThreads")
    suspend fun videoComments(
        @Query("key") key: String,
        @Query("videoId") order: String,
        @Query("part") part: String
    ): Response<CommentResponse>

}