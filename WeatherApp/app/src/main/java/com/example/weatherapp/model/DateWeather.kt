package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class DateWeather constructor(
        @SerializedName("id") var id: Int,
        @SerializedName("datetime") var datetime: Long,
        @SerializedName("temp") var temp: Temperature,
        @SerializedName("feels_like") var feels_like: FeelsLike,
        @SerializedName("pressure") var pressure: Int,
        @SerializedName("humidity") var humidity: Int,
        @SerializedName("description")var description: String
)