package com.manuflowers.photoInspiration.application

import android.app.Application
import androidx.room.Room
import com.manuflowers.photoInspiration.data.database.PhotosDatabase

class QuickNewsApplication: Application() {

    companion object {
        private lateinit var instance: QuickNewsApplication

        val PHOTOS_DATABASE: PhotosDatabase by lazy {
            Room.databaseBuilder(instance, PhotosDatabase::class.java, "photos_database")
                .build()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}