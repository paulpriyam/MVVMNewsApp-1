package com.androiddevs.mvvmnewsapp.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = "article"
)
data class Article(

    @PrimaryKey(autoGenerate = true                       )
    var id: Int? = null,
    @SerializedName("author")
    val author: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("publishedAt")
    val publishedAt: String,
    @SerializedName("source")
    val source: Source,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
)