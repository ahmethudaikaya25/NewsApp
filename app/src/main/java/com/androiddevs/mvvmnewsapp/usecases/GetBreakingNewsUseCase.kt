package com.androiddevs.mvvmnewsapp.usecases

import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository

class GetBreakingNewsUseCase(val repository: NewsRepository) {
    suspend fun invoke(countryCode: String, pageNumber: Int) = repository.getBreakingNews(countryCode, pageNumber)
}