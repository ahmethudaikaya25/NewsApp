package com.androiddevs.mvvmnewsapp.usecases

import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository

class SearchArticleUseCase (private val repository: NewsRepository){
    suspend fun invoke(query: String, pageNumber: Int) = repository.searchNews(query, pageNumber)
}