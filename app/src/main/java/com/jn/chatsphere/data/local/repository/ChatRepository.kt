package com.jn.chatsphere.data.local.repository

import com.jn.chatsphere.data.local.entities.ChatEntity
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    suspend fun upsert(chat: ChatEntity)

    suspend fun insert(chat: ChatEntity)

    suspend fun update(chat: ChatEntity)

    suspend fun getAllChat(): Flow<List<ChatEntity>>

    suspend fun getChatById(id: Int): Flow<ChatEntity>

    suspend fun delete(chat: ChatEntity)
}