package com.androiddevs.mvvmnewsapp.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.annotation.Nullable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(
    tableName = "article"
)
data class Article(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("content")
    val content: String?=null,
    @SerializedName("description")
    val description: String?=null,
    @SerializedName("publishedAt")
    val publishedAt: String?=null,
    @SerializedName("source")
    val source: Source?=null,
    @SerializedName("title")
    val title: String?=null,
    @SerializedName("url")
    val url: String,
    @SerializedName("urlToImage")
    val urlToImage: String
) : java.io.Serializable