package com.androiddevs.mvvmnewsapp.usecases

import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository

class GetAllFavoritesUseCase(val repository: NewsRepository) {
    fun invoke() =  repository.getAllArticles()
}