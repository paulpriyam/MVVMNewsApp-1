package com.androiddevs.mvvmnewsapp.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.androiddevs.mvvmnewsapp.NewsApplication
import com.androiddevs.mvvmnewsapp.model.Article
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.androiddevs.mvvmnewsapp.paging.NewsPagingSource
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utils.ResponseWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    app: Application,
    private val newsRepository: NewsRepository
) : AndroidViewModel(app) {

    private var _newsLiveData = MutableLiveData<ResponseWrapper<NewsResponse>>()
    val newsLiveData: LiveData<ResponseWrapper<NewsResponse>> get() = _newsLiveData

    private var _searchNewsLiveData = MutableLiveData<ResponseWrapper<NewsResponse>>()
    val searchNewsLiveData: LiveData<ResponseWrapper<NewsResponse>> get() = _searchNewsLiveData

    private val countryCode = "in"
    private val pageSize = 10
    private val searchPageSiz = 10
    private val page = 1
    private val searchPage = 1
    var newsPager: Flow<PagingData<Article>>? = null
    var searchNewsPager: Flow<PagingData<Article>>? = null


    fun getBreakingNews() {
        viewModelScope.launch {
            newsPager = Pager(PagingConfig(pageSize = 20)) {
                NewsPagingSource(newsRepository, "in")
            }.flow.cachedIn(viewModelScope)
        }
//        _newsLiveData.postValue(ResponseWrapper.Loading())
//        val response = newsRepository.getBreakingNews(countryCode, pageSize, page)
//        _newsLiveData.postValue(handleNewsResponse(response))
    }


    fun getSearchNews(searchText: String) {
        viewModelScope.launch {
            searchNewsPager = Pager(PagingConfig(pageSize = 20)) {
                NewsPagingSource(newsRepository, null, searchText)
            }.flow.cachedIn(viewModelScope)
        }
    }

    private fun handleNewsResponse(response: Response<NewsResponse>): ResponseWrapper<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponseWrapper.Success(response.body())
            }
        }
        return ResponseWrapper.Error(response.message())
    }

    private fun handleSearchedNewsResponse(response: Response<NewsResponse>): ResponseWrapper<NewsResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResponseWrapper.Success(response.body())
            }
        }
        return ResponseWrapper.Error(response.message())
    }

    fun savedArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getSavedArticles() = newsRepository.getAllSavedArticles()

    fun hasNetworkConnection(): Boolean {
        val connectivityManager = getApplication<NewsApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= 23) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.apply {
                return when (type) {
                    TYPE_WIFI -> true
                    TYPE_MOBILE -> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
            return false
        }
    }

}