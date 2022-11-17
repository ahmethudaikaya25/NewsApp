package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.util.InternetStateProvider

class NewsViewModelProviderFactory(
    val internetStateProvider: InternetStateProvider,
    val newsRepository: NewsRepository
) : Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(internetStateProvider, newsRepository) as T
    }
}