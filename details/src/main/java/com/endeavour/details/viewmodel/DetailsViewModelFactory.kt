package com.endeavour.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DetailsViewModelFactory (
    private val repository: com.endeavour.domain.repo.GMBNRepository,
    private val videoId: String
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return com.endeavour.details.viewmodel.DetailsViewModel(repository, videoId) as T
    }
}