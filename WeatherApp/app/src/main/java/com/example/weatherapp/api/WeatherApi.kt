package com.example.weatherapp.api

import com.google.gson.JsonObject
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("daily?appid=60c6fbeb4b93ac653c492ba806fc346d")
    suspend fun getDailyForecast(@Query("cnt") cnt : Int, @Query("q") q : String): Response<JsonObject>
}