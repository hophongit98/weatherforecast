package com.example.weatherapp.model

import com.google.gson.annotations.SerializedName

data class Temperature constructor(
        @SerializedName("day") var day: Float = -1.0f,
        @SerializedName("min") var min: Float = -1.0f,
        @SerializedName("max") var max: Float = -1.0f,
        @SerializedName("night") var night: Float = -1.0f,
        @SerializedName("eve") var eve: Float = -1.0f,
        @SerializedName("morn") var morn: Float = -1.0f
)