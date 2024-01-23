package com.jn.chatsphere.data.converters

import androidx.room.TypeConverter
import com.jn.chatsphere.domain.model.ChatSetting

class ChatSettingConverter {

    @TypeConverter
    fun fromChatSetting(chatSetting: ChatSetting): String {
        return chatSetting.title
    }

    @TypeConverter
    fun toChatSetting(chatSettingString: String): ChatSetting {
        return ChatSetting(chatSettingString)
    }
}