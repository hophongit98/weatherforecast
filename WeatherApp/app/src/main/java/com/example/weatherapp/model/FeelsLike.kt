package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class FeelsLike constructor(
        @SerializedName("day") var day: Float = -1.0f,
        @SerializedName("night") var night: Float = -1.0f,
        @SerializedName("eve") var eve: Float = -1.0f,
        @SerializedName("morn") var morn: Float = -1.0f
)