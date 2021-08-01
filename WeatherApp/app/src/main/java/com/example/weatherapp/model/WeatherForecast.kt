package com.example.weatherapp.model

import androidx.room.Entity
import com.example.weatherapp.model.WeatherForecast.Companion.TABLE_NAME
import com.google.gson.annotations.SerializedName

@Entity(tableName = TABLE_NAME, primaryKeys = ["id"])
data class WeatherForecast(
    @SerializedName( "id") var id: Int,
    @SerializedName("city") var city: String,
    @SerializedName("dateWeather") var dateWeather: MutableList<DateWeather>,
    @SerializedName("createAt") var createAt: Int
) {
    companion object {
        const val TABLE_NAME = "weather"
    }
}