package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.model.Article
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.usecases.AddFavoriteArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    val insertArticleUseCase: AddFavoriteArticleUseCase
) : ViewModel(){
        fun saveArticle(article: Article) = viewModelScope.launch {
            insertArticleUseCase.invoke(article)
        }
}