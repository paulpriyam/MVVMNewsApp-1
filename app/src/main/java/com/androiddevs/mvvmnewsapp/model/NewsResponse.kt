package com.androiddevs.mvvmnewsapp.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NewsResponse(
    @SerializedName("articles")
    val articles: List<Article>?,
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int
)