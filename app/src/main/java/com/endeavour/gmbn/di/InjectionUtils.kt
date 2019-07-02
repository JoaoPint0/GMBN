package com.endeavour.gmbn.di

import android.content.Context
import com.endeavour.gmbn.api.YoutubeService
import com.endeavour.gmbn.db.GMBNDatabase
import com.endeavour.gmbn.repository.GMBNRepository
import com.endeavour.gmbn.ui.details.DetailsViewModelFactory
import com.endeavour.gmbn.ui.main.MainViewModelFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InjectionUtils {

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = getGMBNRepository(context)
        return MainViewModelFactory(repository)
    }

    fun provideDetailsViewModelFactory(context: Context, videoId: String): DetailsViewModelFactory {
        val repository = getGMBNRepository(context)
        return DetailsViewModelFactory(repository, videoId)
    }

    private fun getGMBNRepository(context: Context): GMBNRepository {
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