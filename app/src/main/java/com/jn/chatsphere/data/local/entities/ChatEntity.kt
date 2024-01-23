package com.jn.chatsphere.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jn.chatsphere.data.converters.ChatSettingConverter
import com.jn.chatsphere.data.converters.MessagesConverter
import com.jn.chatsphere.domain.model.ChatSetting
import com.jn.chatsphere.domain.model.Message

@Entity
data class ChatEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @TypeConverters(ChatSettingConverter::class)
    val chatSetting: ChatSetting = ChatSetting(),
    @TypeConverters(MessagesConverter::class)
    val messages: List<Message> = emptyList()
)


data class Chat(
    val id: Int = 0,
    val chatSetting: ChatSetting? = ChatSetting(),
    val messages: List<Message> = emptyList()
)


fun ChatEntity.toChat() = Chat(
    id = id,
    chatSetting = chatSetting,
    messages = messages.toList()
)

fun Chat.toChatEntity() = ChatEntity(
    id = id,
    chatSetting = chatSetting ?: ChatSetting(),
    messages = messages.toList()
)