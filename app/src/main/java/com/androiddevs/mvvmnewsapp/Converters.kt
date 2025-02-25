package com.androiddevs.mvvmnewsapp

import androidx.room.TypeConverter
import com.androiddevs.mvvmnewsapp.model.Source

class Converters {

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }
}