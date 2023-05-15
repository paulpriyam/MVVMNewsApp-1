package com.androiddevs.mvvmnewsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.androiddevs.mvvmnewsapp.repository.NewsRepository
import com.androiddevs.mvvmnewsapp.utils.ResponseWrapper
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    private var _newsLiveData = MutableLiveData<ResponseWrapper<NewsResponse>>()
    val newsLiveData: LiveData<ResponseWrapper<NewsResponse>> get() = _newsLiveData

    private var _searchNewsLiveData = MutableLiveData<ResponseWrapper<NewsResponse>>()
    val searchNewsLiveData: LiveData<ResponseWrapper<NewsResponse>> get() = _searchNewsLiveData

    private val countryCode = "in"
    private val pageSize = 10
    private val searchPageSiz = 10
    private val page = 1
    private val searchPage = 1



    fun getBreakingNews() = viewModelScope.launch {
        _newsLiveData.postValue(ResponseWrapper.Loading())
        val response = newsRepository.getBreakingNews(countryCode, pageSize, page)
        _newsLiveData.postValue(handleNewsResponse(response))
    }

    fun getSearchNews(searchText: String) = viewModelScope.launch {
        _searchNewsLiveData.postValue(ResponseWrapper.Loading())
        val response = newsRepository.getSearchedNews(searchText, searchPageSiz, searchPage)
        _searchNewsLiveData.postValue(handleSearchedNewsResponse(response))
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

}