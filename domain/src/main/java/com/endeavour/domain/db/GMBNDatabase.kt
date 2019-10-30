package com.endeavour.domain.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.endeavour.core.vo.Video

@Database(entities = [Video::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GMBNDatabase: RoomDatabase() {

    abstract fun videoDao(): VideoDao

    companion object {

        @Volatile private var instance: GMBNDatabase? = null

        fun getInstance(context: Context): GMBNDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GMBNDatabase {
            return Room.databaseBuilder(context, GMBNDatabase::class.java, "gmbn.db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}