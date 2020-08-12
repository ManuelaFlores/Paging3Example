package com.manuflowers.photoInspiration.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "photo_table")
data class PhotoEntity(
    @PrimaryKey
    val id: String,
    val createdAt: String?,
    val altDescription: String?,
    val smallUrl: String,
    val regularUrl: String,
    val userName: String,
    val userProfileImage: String,
    val userBio: String?,
    val userLocation: String?,
    val userProfileWeb: String?,
    val page: Int = 0,
    val remoteName: String = ""
) : Parcelable