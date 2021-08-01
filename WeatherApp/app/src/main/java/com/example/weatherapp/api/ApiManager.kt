package com.example.weatherapp.api

import com.example.weatherapp.utils.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class ApiManager {

    private var mWeatherApi: WeatherApi

    init {
        mWeatherApi = initWeatherApi()
    }

    fun weatherApi(): WeatherApi = mWeatherApi

    private fun initWeatherApi(): WeatherApi {
        val baseUrl = Constant.WEATHER_BASE_URL
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return ServiceBuilder.baseUrl(baseUrl)
            .client(httpClient)
            .create(WeatherApi::class.java)
    }
}