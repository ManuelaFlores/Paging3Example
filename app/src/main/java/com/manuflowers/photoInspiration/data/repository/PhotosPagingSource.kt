package com.manuflowers.photoInspiration.data.repository

import androidx.paging.PagingSource
import com.manuflowers.photoInspiration.data.model.PhotoResponse
import com.manuflowers.photoInspiration.data.networking.PhotosApi
import retrofit2.HttpException
import java.io.IOException

class PhotosPagingSource(
    private val service: PhotosApi
) : PagingSource<Int, PhotoResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoResponse> {

        val position = params.key ?: PHOTOS_STARTING_PAGE_INDEX

        return try {

            val response = service.getPhotos(
                clientId = CLIENT_ID,
                page = position,
                pageSize = params.loadSize
            )

            LoadResult.Page(
                data = response,
                prevKey = if (position == PHOTOS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }

    }
}

const val PHOTOS_STARTING_PAGE_INDEX = 1