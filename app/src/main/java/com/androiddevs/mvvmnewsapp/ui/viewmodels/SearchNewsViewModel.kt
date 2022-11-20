package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.model.NewsResponse
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.usecases.SearchArticleUseCase
import com.androiddevs.mvvmnewsapp.util.InternetStateProvider
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val internetStateProvider: InternetStateProvider,
    private val searchNewsUseCase: SearchArticleUseCase,
    private val newsRepository: NewsRepository
) : ViewModel() {
    val searchedNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

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
}