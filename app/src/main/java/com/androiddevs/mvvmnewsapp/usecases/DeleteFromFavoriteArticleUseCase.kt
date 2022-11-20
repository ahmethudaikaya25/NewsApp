package com.androiddevs.mvvmnewsapp.usecases

import com.androiddevs.mvvmnewsapp.data.model.Article
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository

class DeleteFromFavoriteArticleUseCase(private val repository: NewsRepository){
    suspend fun invoke(article: Article) = repository.deleteArticle(article)
}