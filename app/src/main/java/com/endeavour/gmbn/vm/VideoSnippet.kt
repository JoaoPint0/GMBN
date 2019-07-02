package com.endeavour.gmbn.vm

import com.endeavour.gmbn.utils.toDuration
import java.util.*

data class VideoSnippet(
    val publishedAt: Date,
    val title: String,
    val description: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(val maxres: Thumbnail)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

data class VideoDetailed(
    val id: String,
    val snippet: VideoSnippet,
    val contentDetails: ContentDetails,
    val statistics: Statistics) {

    fun toEntity() = Video(
        id,
        snippet.thumbnails.maxres.url,
        snippet.title,
        snippet.description,
        snippet.publishedAt,
        contentDetails.duration.toDuration())
}

data class ContentDetails(val duration : String)

data class Statistics(
    val viewCount: String,
    val likeCount: String,
    val dislikeCount: String
)
data class VideoApi(val id: VideoId){
    fun toId() = this.id.videoId
}
data class VideoId(
    val kind: String,
    val videoId: String
)

data class VideoResponse(val items: List<VideoApi>)

data class VideoDetailedResponse(val items: List<VideoDetailed>)