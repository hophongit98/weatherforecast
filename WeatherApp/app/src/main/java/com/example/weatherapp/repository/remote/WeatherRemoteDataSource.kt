package com.example.weatherapp.repository.remote

import android.util.Log
import com.example.weatherapp.api.ApiManager
import com.example.weatherapp.utils.DataResponse
import com.example.weatherapp.utils.handleRequest
import com.google.gson.JsonObject

class WeatherRemoteDataSource {

    companion object {
        private val TAG = WeatherRemoteDataSource::class.java.simpleName
    }

    private val mApiManager = ApiManager()

    suspend fun fetchData(days: Int, location: String): DataResponse<JsonObject> {
        Log.d(TAG, "fetchData")
        return handleRequest { mApiManager.weatherApi().getDailyForecast(days, location) }
    }
}