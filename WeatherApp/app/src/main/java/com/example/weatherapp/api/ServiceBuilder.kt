package com.example.weatherapp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val mRetrofitBuilder = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())

    fun baseUrl(url: String) = apply {
        mRetrofitBuilder.baseUrl(url)
    }

    fun client(client: OkHttpClient) = apply {
        mRetrofitBuilder.client(client)
    }

    fun <S> create(apiClass: Class<S>): S = mRetrofitBuilder.build().create(apiClass)
}