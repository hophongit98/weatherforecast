package com.example.weatherapp.utils

import android.util.Log
import com.example.weatherapp.utils.ErrorCode.SOCKET_TIME_OUT
import com.example.weatherapp.utils.ErrorCode.UNKNOWN
import com.example.weatherapp.utils.ErrorCode.UNKNOWN_HOST
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

@Synchronized
fun <T> T.toJsonString(): String {
    val type = object : TypeToken<T>() {}.type
    return Gson().toJson(this, type)
}

/**
 * Convert milliseconds to seconds
 */

fun Long.toSecond(): Int {
    return if (this > Int.MAX_VALUE.toLong()) {
        (this / 1000).toInt()
    } else {
        this.toInt()
    }
}

suspend fun <T: Any?> handleRequest(requestFunc: suspend () -> Response<T>): DataResponse<T> {
    return try {
        requestFunc.invoke().getDataResponse()
    } catch (e: Exception) {
        Log.d("handleRequest", "handle exception - e=$e")
        val errorCode = when (e) {
            is SocketTimeoutException -> SOCKET_TIME_OUT
            is UnknownHostException -> UNKNOWN_HOST
            else -> UNKNOWN
        }
        Failure(errorCode, "${e.message}")
    }
}

fun <T: Any?> Response<T>.getDataResponse(): DataResponse<T> {
    return if (this.isSuccessful) {
        Success(this.body())
    } else {
        when (val code = this.code()) {
            in arrayOf(HttpURLConnection.HTTP_GATEWAY_TIMEOUT, HttpURLConnection.HTTP_UNAVAILABLE, HttpURLConnection.HTTP_INTERNAL_ERROR,
                    HttpURLConnection.HTTP_UNAUTHORIZED) -> {
                val errorMsg = this.errorBody().toString()
                Failure(code, errorMsg)
            }
            else -> {
                Failure(code, this.message())
            }
        }
    }
}