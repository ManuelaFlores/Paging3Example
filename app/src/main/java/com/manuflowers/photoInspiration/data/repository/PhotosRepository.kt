package com.manuflowers.photoInspiration.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.manuflowers.photoInspiration.data.database.PhotosDatabase
import com.manuflowers.photoInspiration.data.model.PhotoEntity
import com.manuflowers.photoInspiration.data.networking.PhotosApi
import kotlinx.coroutines.flow.Flow

class PhotosRepository(
    private val photosApi: PhotosApi,
    private val database: PhotosDatabase
) {
    @ExperimentalPagingApi
    fun getSearchResultsNews(): Flow<PagingData<PhotoEntity>> {
        val pagingSourceFactory =  { database.photosDao().getAllPhotos()}
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 4,
                initialLoadSize = 30
            ),
            remoteMediator =  PhotosRemoteMediator(service = photosApi, database = database),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 30
    }
}