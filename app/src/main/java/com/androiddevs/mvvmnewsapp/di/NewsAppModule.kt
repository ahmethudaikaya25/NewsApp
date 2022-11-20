package com.androiddevs.mvvmnewsapp.di

import android.content.Context
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.usecases.*
import com.androiddevs.mvvmnewsapp.util.InternetStateChecker
import com.androiddevs.mvvmnewsapp.util.InternetStateProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NewsAppModule {
    @Provides
    fun provideInternetStateChecker(
        @ApplicationContext context: Context
    ): InternetStateProvider = InternetStateChecker(context = context)

    @Provides
    fun provideGetBreakingNewsUseCase(
        repository: NewsRepository
    ): GetBreakingNewsUseCase = GetBreakingNewsUseCase(repository)

    @Provides
    fun provideAddFavoriteUseCase(
        repository: NewsRepository
    ): AddFavoriteArticleUseCase = AddFavoriteArticleUseCase(repository)

    @Provides
    fun provideGetAllFavoritesUseCase(
        repository: NewsRepository
    ): GetAllFavoritesUseCase = GetAllFavoritesUseCase(repository)

    @Provides
    fun provideDeleteFromFavoriteUseCase(
        repository: NewsRepository
    ): DeleteFromFavoriteArticleUseCase = DeleteFromFavoriteArticleUseCase(repository)

    @Provides
    fun provideSearchArticlesUseCase(
        repository: NewsRepository
    ): SearchArticleUseCase = SearchArticleUseCase((repository))
}