package com.manuflowers.photoInspiration.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manuflowers.photoInspiration.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: RemoteKeys)

    @Query("SELECT * FROM remote_keys WHERE remoteName = :articleId")
    suspend fun remoteKeysArticleId(articleId: Int): RemoteKeys

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}