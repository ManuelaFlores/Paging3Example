package com.manuflowers.photoInspiration.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val remoteName: String,
    val nextKey: Int?
)