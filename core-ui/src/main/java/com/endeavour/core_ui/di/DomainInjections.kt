package com.endeavour.core_ui.di

import android.content.Context
import com.endeavour.domain.api.YoutubeService
import com.endeavour.domain.db.GMBNDatabase
import com.endeavour.domain.repo.GMBNRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DomainInjections {

    fun provideGMBNRepository(context: Context): GMBNRepository {
        return GMBNRepository
            .getInstance(provideYoutubeService(), GMBNDatabase.getInstance(context.applicationContext).videoDao())
    }

    private fun provideYoutubeService(): YoutubeService {

        return Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YoutubeService::class.java)
    }
}