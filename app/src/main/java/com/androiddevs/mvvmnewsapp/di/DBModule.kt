package com.androiddevs.mvvmnewsapp.di

import android.content.Context
import androidx.room.Room
import com.androiddevs.mvvmnewsapp.data.api.NewsAPI
import com.androiddevs.mvvmnewsapp.data.db.ArticleDao
import com.androiddevs.mvvmnewsapp.data.db.ArticleDatabase
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DBModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): ArticleDatabase {
        return Room.databaseBuilder(
            context,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }

    @Provides
    fun provideArticleDao(
        database: ArticleDatabase
    ): ArticleDao {
        return database.getArticleDao()
    }

    @Provides
    fun provideNewsRepository(
        api: NewsAPI,
        dao: ArticleDao
    ): NewsRepository = NewsRepository(api, dao)
}