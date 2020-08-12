package com.manuflowers.photoInspiration.data.networking

import com.manuflowers.photoInspiration.data.model.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApi {

    @GET("photos")
    suspend fun getPhotos(
        @Query("client_id") clientId: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<PhotoResponse>
}