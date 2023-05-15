package com.androiddevs.mvvmnewsapp.repository

import com.androiddevs.mvvmnewsapp.RetrofitInstance
import com.androiddevs.mvvmnewsapp.db.NewsDatabase
import com.androiddevs.mvvmnewsapp.model.Article

class NewsRepository(
    val db: NewsDatabase
) {

    suspend fun getBreakingNews(countryCode: String, pageSize: Int, page: Int) =
        RetrofitInstance.newsApi.getBreakingNews(countryCode, pageSize, page)

    suspend fun getSearchedNews(searchedText: String, pageSize: Int, page: Int) =
        RetrofitInstance.newsApi.searchNews(searchedText, pageSize, page)

    suspend fun upsert(article: Article) = db.getNewsDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getNewsDao().deleteArticle(article)

    fun getAllSavedArticles() = db.getNewsDao().getAllNews()
}