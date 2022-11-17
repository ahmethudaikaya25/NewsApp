package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.model.Article
import com.androiddevs.mvvmnewsapp.data.model.NewsResponse
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.util.Constants
import com.androiddevs.mvvmnewsapp.util.InternetStateProvider
import com.androiddevs.mvvmnewsapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class NewsViewModel(
    val internetStateProvider: InternetStateProvider,
    val newsRepository: NewsRepository
) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    init {
        getBreakingNews(Constants.DEFAULT_COUNTRY_CODE)
    }

    fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        safeBreakingNews(countryCode)
    }

    private suspend fun safeBreakingNews(countryCode: String) {
        breakingNews.postValue(Resource.Loading())
        try {
            if (internetStateProvider.check()) {
                val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
                breakingNews.postValue(handleBreakingNewsResponse(response))
            } else {
                breakingNews.postValue(Resource.Error(message = "There is no internet"))
            }
        } catch (thr : Throwable) {
            when(thr) {
                is IOException -> breakingNews.postValue(Resource.Error(message = "Network Failure"))
                else -> breakingNews.postValue(Resource.Error(message = "Getting Breaking News Error"))
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { newsResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null) {
                    breakingNewsResponse = newsResponse
                } else {
                    val oldArticles = breakingNewsResponse?.articles
                    val newArticles = newsResponse.articles
                    oldArticles?.addAll(newArticles)
                }

                return Resource.Success(breakingNewsResponse ?: newsResponse)
            }
        }
        return Resource.Error(message = response.message())
    }

    fun searchNews(queryParameter: String) = viewModelScope.launch {
        safeSearchingNews(queryParameter)
    }

    private suspend fun safeSearchingNews(searchQuery: String) {
        searchedNews.postValue(Resource.Loading())
        try {
            if (internetStateProvider.check()) {
                val response = newsRepository.searchNews(searchQuery, searchNewsPage)
                searchedNews.postValue(handleSearchNewsResponse(response))
            } else {
                searchedNews.postValue(Resource.Error(message = "There is no internet"))
            }
        } catch (thr : Throwable) {
            when(thr) {
                is IOException -> searchedNews.postValue(Resource.Error(message = "Network Failure"))
                else -> searchedNews.postValue(Resource.Error(message = "Searching News Error"))
            }
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                searchNewsPage++
                if (searchNewsResponse == null) {
                    searchNewsResponse = resultResponse
                } else {
                    val oldArticles = searchNewsResponse?.articles
                    val newArticles = resultResponse.articles
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(searchNewsResponse ?: resultResponse)
            }
        }
        return Resource.Error(message = response.message())
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsertArticle(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getSavedArticles() = newsRepository.getAllArticles()
}