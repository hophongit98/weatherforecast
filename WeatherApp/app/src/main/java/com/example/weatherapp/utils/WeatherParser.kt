package com.example.weatherapp.utils

import android.util.Log
import com.example.weatherapp.model.DateWeather
import com.example.weatherapp.model.FeelsLike
import com.example.weatherapp.model.Temperature
import com.example.weatherapp.model.WeatherForecast
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.Exception

object WeatherParser {

    private val TAG = WeatherParser::class.java.simpleName

    fun parse(jObject: JsonObject?): WeatherForecast? {
        if (jObject == null) return null

        return try {
            val city = jObject.get("city").asJsonObject
            val id = city.get("id").asInt
            val name = city.get("name").asString
            val listData = jObject.get("list").asJsonArray
            val listDate = arrayListOf<DateWeather>()

            listData.forEach { ele ->
                val obj = ele.asJsonObject
                val dt = obj.get("dt").asLong
                val pressure = obj.get("pressure").asInt
                val humidity = obj.get("humidity").asInt
                val temp = getTemp(obj.get("temp"))
                val feelsLike = getFeelsLike(obj.get("feels_like"))
                val (id, description) = getDescription(obj.get("weather"))
                listDate.add(DateWeather(id, dt, temp, feelsLike, pressure, humidity, description))
            }
            WeatherForecast(id, name, listDate, System.currentTimeMillis().toSecond())
        } catch (e: Exception) {
            Log.d(TAG, "parse - e=${e.message}")
            null
        }
    }

    private fun getTemp(ele: JsonElement): Temperature {
        return try {
            val obj = ele.asJsonObject
            val day = obj.get("day").asFloat
            val night = obj.get("night").asFloat
            val min = obj.get("min").asFloat
            val max = obj.get("max").asFloat
            val eve = obj.get("eve").asFloat
            val morn = obj.get("morn").asFloat
            Temperature(day, max, min, night,eve, morn)
        } catch (e: Exception) {
            Temperature()
        }
    }

    private fun getFeelsLike(ele: JsonElement): FeelsLike {
        return try {
            val obj = ele.asJsonObject
            val day = obj.get("day").asFloat
            val night = obj.get("night").asFloat
            val eve = obj.get("eve").asFloat
            val morn = obj.get("morn").asFloat
            FeelsLike(day, night,eve, morn)
        } catch (e: Exception) {
            FeelsLike()
        }
    }

    private fun getDescription(jEle: JsonElement): Pair<Int, String> {
        return try {
            val obj = jEle.asJsonArray[0].asJsonObject
            val des = obj.get("description").asString
            val id = obj.get("id").asInt
            Pair(id, des)
        } catch (e: Exception) {
            Pair(-1, "")
        }
    }
}