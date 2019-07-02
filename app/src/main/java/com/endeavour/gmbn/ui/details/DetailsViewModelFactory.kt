package com.endeavour.gmbn.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.endeavour.gmbn.repository.GMBNRepository

class DetailsViewModelFactory (
    private val repository: GMBNRepository,
    private val videoId: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository, videoId) as T
    }
}