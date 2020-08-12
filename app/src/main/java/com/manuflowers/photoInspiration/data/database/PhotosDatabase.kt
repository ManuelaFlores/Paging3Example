package com.manuflowers.photoInspiration.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.manuflowers.photoInspiration.data.model.PhotoEntity
import com.manuflowers.photoInspiration.data.model.RemoteKeys

@Database(
    entities = [PhotoEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class PhotosDatabase: RoomDatabase() {
    abstract fun photosDao(): PhotosDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}