package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.NewsDatabase

class NewsRepository(
    val db: NewsDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageSize: Int, page: Int) =
        RetrofitInstance.newsApi.getBreakingNews(countryCode, pageSize, page)

    suspend fun getSearchedNews(searchedText: String, pageSize: Int, page: Int) =
        RetrofitInstance.newsApi.searchNews(searchedText, pageSize, page)
}