package com.androiddevs.mvvmnewsapp.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.data.model.NewsResponse
import com.androiddevs.mvvmnewsapp.ui.repositories.NewsRepository
import com.androiddevs.mvvmnewsapp.usecases.GetBreakingNewsUseCase
import com.androiddevs.mvvmnewsapp.util.Constants
import com.androiddevs.mvvmnewsapp.util.InternetStateProvider
import com.androiddevs.mvvmnewsapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class BreakingNewsViewModel @Inject constructor(
    private val internetStateProvider: InternetStateProvider,
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase
) : ViewModel() {
    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null


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
                val response = getBreakingNewsUseCase.invoke(countryCode, breakingNewsPage)
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
}