package com.example.weatherapp.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.model.WeatherForecast
import com.example.weatherapp.repository.local.WeatherLocalDataSource
import com.example.weatherapp.repository.remote.WeatherRemoteDataSource
import com.example.weatherapp.utils.*

class WeatherRepository private constructor(private val local: WeatherLocalDataSource, private val remote: WeatherRemoteDataSource) {

    companion object {
        private val TAG = WeatherRepository::class.java.simpleName

        @Volatile
        private var instance: WeatherRepository? = null

        fun getInstance(context: Context): WeatherRepository {
            instance?.let {
                return it
            }

            return synchronized(this) {
                var i2 = instance
                if (i2 != null) {
                    i2
                } else {
                    i2 = WeatherRepository(WeatherLocalDataSource(context.applicationContext), WeatherRemoteDataSource())
                    instance = i2
                    i2
                }
            }
        }
    }

    suspend fun fetchData(days: Int, location: String): Response {
        Log.d(TAG, "fetchData")
        when (val response = remote.fetchData(days, location)) {
            is Success -> {
                Log.d(TAG, "fetchData - Success")
                WeatherParser.parse(response.data)?.let { data ->
                    local.insert(data)
                    return ResponseSuccess()
                } ?: kotlin.run {
                    Log.d(TAG, "fetchData - Success - parse data fail")
                    return ResponseFail(ErrorCode.UNKNOWN, "parse data fail")
                }

            }
            is Failure -> {
                Log.i(TAG, "fetchData - Failure - res=${response}")
                return ResponseFail(response.errorCode, response.errorMsg)
            }
            else -> {
                return ResponseFail(ErrorCode.UNKNOWN, "Response invalid!!!")
            }
        }
    }

    fun getLatestForecast(): WeatherForecast? {
        Log.d(TAG, "getLatestForecast")
        return local.getLatestForecast()
    }

    fun getLatestForecastLive(): LiveData<WeatherForecast?> {
        Log.d(TAG, "getLatestForecastLive")
        return local.getLatestForecastLive()
    }

    fun getAllWeatherForecast(): List<WeatherForecast> {
        Log.d(TAG, "getAllWeatherForecast")
        return local.getAllWeatherForecast()
    }

    class ResponseSuccess: ResponseValue
    data class ResponseFail(val errorCode: Int, val errorMsg: String): ResponseError
}