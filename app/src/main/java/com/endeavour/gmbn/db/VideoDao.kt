package com.endeavour.gmbn.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.endeavour.gmbn.vm.Video

@Dao
interface VideoDao {

    @Query("SELECT * FROM video ORDER BY publishedAt DESC")
    fun getVideos(): LiveData<List<Video>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(currencies: List<Video>)

    @Update
    suspend fun updateRates(items: List<Video>)

    @Query("SELECT * FROM video  WHERE id = :videoId  ")
    fun loadVideoById(videoId: String): LiveData<Video>

    @Query("SELECT * FROM video ORDER BY publishedAt DESC")
    fun loadCachedVideos(): List<Video>

}