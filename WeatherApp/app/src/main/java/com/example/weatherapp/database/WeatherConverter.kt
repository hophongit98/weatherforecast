package com.example.weatherapp.database

import android.util.Log
import androidx.room.TypeConverter
import com.example.weatherapp.model.DateWeather
import com.example.weatherapp.model.FeelsLike
import com.example.weatherapp.model.Temperature
import com.example.weatherapp.utils.toJsonString
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherConverter {

    companion object {
        private val TAG = WeatherConverter::class.java.simpleName
    }

    @TypeConverter
    fun fromFeelsLike(feelsLike: FeelsLike?): String {
        if (feelsLike == null) return ""

        return try {
            feelsLike.toJsonString()
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "fromFeelsLike - e=${e.message}")
            ""
        }
    }

    @TypeConverter
    fun toFeelsLike(data: String?): FeelsLike? {
        if (data == null) return null

        return try {
            val type = object : TypeToken<FeelsLike>() {}.type
            Gson().fromJson(data, type)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "toFeelsLike - e=${e.message}")
            null
        }
    }

    @TypeConverter
    fun fromTemperature(temperature: Temperature?): String {
        if (temperature == null) return ""

        return try {
            temperature.toJsonString()
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "fromWeather - e=${e.message}")
            ""
        }
    }

    @TypeConverter
    fun toTemperature(data: String?): Temperature? {
        if (data == null) return null

        return try {
            val type = object : TypeToken<Temperature>() {}.type
            Gson().fromJson(data, type)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "toTemperature - e=${e.message}")
            null
        }
    }

    @TypeConverter
    fun fromDateWeather(dateWeather: DateWeather?): String {
        if (dateWeather == null) return ""

        return try {
            dateWeather.toJsonString()
        } catch (e: Exception) {
            Log.e(TAG, "fromDateWeather - e=${e.message}")
            e.printStackTrace()
            ""
        }
    }

    @TypeConverter
    fun toDateWeather(data: String?): DateWeather? {
        if (data == null) return null

        return try {
            val type = object : TypeToken<DateWeather>() {}.type
            Gson().fromJson(data, type)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "toDateWeather - e=${e.message}")
            null
        }
    }

    @TypeConverter
    fun fromDateWeathers(dateWeather: MutableList<DateWeather>): String {
        if (dateWeather.isEmpty()) return ""

        return try {
            val type = object : TypeToken<ArrayList<DateWeather>>() {}.type
            Gson().toJson(dateWeather, type)
        } catch (e: Exception) {
            Log.e(TAG, "fromDateWeathers - e=${e.message}")
            e.printStackTrace()
            ""
        }
    }

    @TypeConverter
    fun toDateWeathers(data: String?): MutableList<DateWeather> {
        if (data == null) return mutableListOf()

        return try {
            val type = object : TypeToken<MutableList<DateWeather>>() {}.type
            Gson().fromJson(data, type)
        } catch (e: java.lang.Exception) {
            Log.e(TAG, "toDateWeathers - e=${e.message}")
            arrayListOf()
        }
    }
}