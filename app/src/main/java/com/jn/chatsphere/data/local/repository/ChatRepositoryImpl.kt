package com.jn.chatsphere.data.local.repository

import com.jn.chatsphere.data.local.datasource.ChatDao
import com.jn.chatsphere.data.local.entities.ChatEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatDao: ChatDao
): ChatRepository {

    override suspend fun upsert(chat: ChatEntity) = chatDao.upsert(chat)

    override suspend fun insert(chat: ChatEntity) = chatDao.insert(chat)

    override suspend fun update(chat: ChatEntity) = chatDao.update(chat)

    override suspend fun getAllChat(): Flow<List<ChatEntity>> = chatDao.getAllChat()

    override suspend fun getChatById(id: Int): Flow<ChatEntity> = chatDao.getChatById(id)

    override suspend fun delete(chat: ChatEntity) = chatDao.delete(chat)

}