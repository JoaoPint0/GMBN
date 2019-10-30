package com.endeavour.details.di

import android.content.Context
import com.endeavour.core_ui.di.DomainInjections
import com.endeavour.details.viewmodel.DetailsViewModelFactory

object InjectionUtils {

    fun provideDetailsViewModelFactory(context: Context, videoId: String): DetailsViewModelFactory {
        val repository = DomainInjections.provideGMBNRepository(context)
        return DetailsViewModelFactory(repository, videoId)
    }
}