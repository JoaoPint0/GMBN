package com.endeavour.gmbn.vm

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "video")
data class Video(
    @PrimaryKey
    val id: String,
    val thumbnail: String,
    val title: String,
    val description: String,
    val publishedAt: Date,
    val duration: String)