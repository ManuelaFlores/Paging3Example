package com.manuflowers.photoInspiration.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manuflowers.photoInspiration.data.model.PhotoEntity


@Dao
interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<PhotoEntity>)

    @Query("SELECT * FROM photo_table")
    fun getAllPhotos(): PagingSource<Int, PhotoEntity>

    @Query("DELETE FROM photo_table where remoteName = :name")
    suspend fun clearPhotos(name: String)

}