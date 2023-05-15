package com.androiddevs.mvvmnewsapp.api

import com.androiddevs.mvvmnewsapp.model.NewsResponse
import com.androiddevs.mvvmnewsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        country: String,
        @Query("pageSize")
        pageSize: Int,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        searchText: String,
        @Query("pageSize")
        pageSize: Int,
        @Query("page")
        page: Int,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<NewsResponse>
}