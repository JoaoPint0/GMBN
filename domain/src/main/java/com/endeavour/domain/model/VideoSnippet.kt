package com.endeavour.domain.model

import com.endeavour.core.vo.Video
import org.joda.time.format.ISOPeriodFormat
import org.joda.time.format.PeriodFormatterBuilder
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
    val statistics: Statistics
) {

    fun toEntity() = Video(
        id,
        snippet.thumbnails.maxres.url,
        snippet.title,
        snippet.description,
        snippet.publishedAt,
        contentDetails.duration.toDuration()
    )
}

fun String.toDuration(): String {
    val period =  ISOPeriodFormat.standard().parsePeriod(this)
    return  PeriodFormatterBuilder()
        .appendHours()
        .appendSeparator(":")
        .appendMinutes()
        .appendSeparator(":")
        .printZeroAlways()
        .minimumPrintedDigits(2)
        .appendSeconds().toFormatter()
        .print(period)

}

data class ContentDetails(val duration: String)

data class Statistics(
    val viewCount: String,
    val likeCount: String,
    val dislikeCount: String
)

data class VideoApi(val id: VideoId) {
    fun toId() = this.id.videoId
}

data class VideoId(val kind: String, val videoId: String)

data class VideoResponse(val nextPageToken: String, val items: List<VideoApi>)

data class VideoDetailedResponse(val items: List<VideoDetailed>)