package com.jn.chatsphere.data.local.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jn.chatsphere.data.converters.ChatSettingConverter
import com.jn.chatsphere.data.converters.MessagesConverter
import com.jn.chatsphere.data.local.entities.ChatEntity

@Database(entities = [ChatEntity::class], version = 1, exportSchema = false)
@TypeConverters(ChatSettingConverter::class, MessagesConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun chatDao(): ChatDao
}