package com.manuflowers.photoInspiration.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.manuflowers.photoInspiration.data.model.PhotoEntity
import com.manuflowers.photoInspiration.data.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val repository: PhotosRepository
) : ViewModel() {

    @ExperimentalPagingApi
    fun getPhotos(): Flow<PagingData<PhotoEntity>> {
        return repository
            .getSearchResultsNews()
            .cachedIn(viewModelScope)
    }
}