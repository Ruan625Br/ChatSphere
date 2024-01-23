package com.jn.chatsphere.data.local.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.jn.chatsphere.data.local.entities.ChatEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsert(chatEntity: ChatEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(chatEntity: ChatEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(chatEntity: ChatEntity)

    @Query("SELECT * FROM ChatEntity")
    fun getAllChat(): Flow<List<ChatEntity>>

    @Query("SELECT * FROM ChatEntity WHERE id = :id")
    fun getChatById(id: Int): Flow<ChatEntity>

    @Delete
    suspend fun delete(chatEntity: ChatEntity)
}