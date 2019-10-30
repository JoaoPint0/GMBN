package com.endeavour.domain.model

import com.endeavour.core.vo.Comment

data class CommentSnippet(val topLevelComment: TopLevelComment)

data class TopLevelComment(val id: String,
                           val snippet: TopLevelCommentSnippet
)

data class TopLevelCommentSnippet(
    val authorDisplayName: String,
    val authorProfileImageUrl: String,
    val textDisplay: String)

data class CommentApi(val snippet: CommentSnippet){

    fun toEntity() = Comment(
        snippet.topLevelComment.id,
        snippet.topLevelComment.snippet.authorDisplayName,
        snippet.topLevelComment.snippet.authorProfileImageUrl,
        snippet.topLevelComment.snippet.textDisplay
    )
}

data class CommentResponse(val items: List<CommentApi>)