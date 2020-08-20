package com.manuflowers.photoInspiration.data.repository

import androidx.paging.*
import androidx.room.withTransaction
import com.manuflowers.photoInspiration.data.database.PhotosDatabase
import com.manuflowers.photoInspiration.data.model.PhotoEntity
import com.manuflowers.photoInspiration.data.model.RemoteKeys
import com.manuflowers.photoInspiration.data.model.asPhotoEntity
import com.manuflowers.photoInspiration.data.networking.PhotosApi
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PhotosRemoteMediator(
    private val service: PhotosApi,
    private val database: PhotosDatabase
) : RemoteMediator<Int, PhotoEntity>() {

    private val remoteKeyDao = database.remoteKeysDao()
    private val photosDao = database.photosDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PhotoEntity>
    ): MediatorResult {

        try {

            val pageKey = when (loadType) {
                LoadType.REFRESH -> {
                    null

                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull() ?: return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                    lastItem.page
                }
            }

            val page = pageKey ?: 0

            val response = service.getPhotos(
                clientId = CLIENT_ID,
                page = if (page == 0) 1 else page,
                pageSize = state.config.pageSize
            )

            val endOfPaginationReached = response.isEmpty()

            val photosEntityList = response.map {
                it.asPhotoEntity(page = page + 1, remoteName = "remoteName")
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeyDao.clearRemoteKeys()
                    photosDao.clearPhotos("remoteName")
                }
                val nextKey = if (endOfPaginationReached) null else page + 1

                val key = RemoteKeys(remoteName = "remoteName", nextKey = nextKey)

                remoteKeyDao.insertAll(key)
                photosDao.insertPhotos(photosEntityList)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

}


const val CLIENT_ID = "xMgrwRJDt_GLYKsvxJM2b1TUAYenCzlLt0nErWCnc24"