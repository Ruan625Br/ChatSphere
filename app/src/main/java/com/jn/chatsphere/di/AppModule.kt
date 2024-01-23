package com.jn.chatsphere.di

import android.content.Context
import androidx.room.Room
import com.jn.chatsphere.data.local.datasource.AppDatabase
import com.jn.chatsphere.data.local.datasource.ChatDao
import com.jn.chatsphere.data.local.repository.ChatRepository
import com.jn.chatsphere.data.local.repository.ChatRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java, "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: AppDatabase): ChatDao {
        return appDatabase.chatDao()
    }

    @Provides
    @Singleton
    fun provideChatRepository(chatDao: ChatDao): ChatRepository {
        return ChatRepositoryImpl(chatDao)
    }
}