package com.jn.chatsphere.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jn.chatsphere.domain.model.Message

class MessagesConverter {

    @TypeConverter
    fun fromMessages(messages: List<Message>): String {
        return Gson().toJson(messages)
    }

    @TypeConverter
    fun toMessages(messagesString: String): List<Message> {
        val gson = Gson()
        val type = object  : TypeToken<List<Message>>() {}.type
        return gson.fromJson(messagesString, type)
    }
}