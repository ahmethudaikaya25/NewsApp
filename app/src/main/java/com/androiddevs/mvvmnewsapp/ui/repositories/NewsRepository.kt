package com.androiddevs.mvvmnewsapp.ui.repositories

import com.androiddevs.mvvmnewsapp.data.api.NewsAPI
import com.androiddevs.mvvmnewsapp.data.db.ArticleDao
import com.androiddevs.mvvmnewsapp.data.model.Article

class NewsRepository (
    private val api: NewsAPI,
    private val dao: ArticleDao
) {
    suspend fun getBreakingNews(countryCode: String, pageNumber: Int) =
        api.getBreakingNews(countryCode, pageNumber)

    suspend fun searchNews(searchQuery: String, pageNumber: Int) =
        api.searchNews(searchQuery, pageNumber)

    suspend fun upsertArticle(article: Article) =
        dao.upsert(article)

    fun getAllArticles() =
        dao.getAllArticles()

    suspend fun deleteArticle(article: Article) =
        dao.deleteArticle(article)

}