package com.endeavour.feed.di

import android.content.Context
import com.endeavour.core_ui.di.DomainInjections
import com.endeavour.feed.viewmodel.MainViewModelFactory

object InjectionUtils {
    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        val repository = DomainInjections.provideGMBNRepository(context)
        return MainViewModelFactory(repository)
    }
}