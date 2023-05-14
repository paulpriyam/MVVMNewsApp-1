package com.androiddevs.mvvmnewsapp.utils

sealed class ResponseWrapper<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : ResponseWrapper<T>(data)
    class Error<T>(msg: String, data: T? = null) : ResponseWrapper<T>(data, msg)
    class Loading<T> : ResponseWrapper<T>()
}