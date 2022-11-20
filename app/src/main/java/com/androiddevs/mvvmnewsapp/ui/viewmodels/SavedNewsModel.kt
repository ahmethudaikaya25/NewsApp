package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.model.Article
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.usecases.AddFavoriteArticleUseCase
import com.androiddevs.mvvmnewsapp.usecases.DeleteFromFavoriteArticleUseCase
import com.androiddevs.mvvmnewsapp.usecases.GetAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsModel @Inject constructor(
    val addFavoriteArticleUseCase: AddFavoriteArticleUseCase,
    val deleteFromFavoriteArticleUseCase: DeleteFromFavoriteArticleUseCase,
    val getAllFavoritesUseCase: GetAllFavoritesUseCase
) : ViewModel() {
    fun saveArticle(article: Article) = viewModelScope.launch {
        addFavoriteArticleUseCase.invoke(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        deleteFromFavoriteArticleUseCase.invoke(article)
    }

    fun getSavedArticles() = getAllFavoritesUseCase.invoke()
}