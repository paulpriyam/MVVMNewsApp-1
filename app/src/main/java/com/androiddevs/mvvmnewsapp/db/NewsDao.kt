package com.androiddevs.mvvmnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.androiddevs.mvvmnewsapp.model.Article

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article)

    @Query("SELECT * from article")
    fun getAllNews(): LiveData<List<Article>>

    @Delete
    suspend fun deleteArticle(article: Article)
}