package com.example.weatherapp.repository.local

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.weatherapp.database.WeatherDatabase
import com.example.weatherapp.model.WeatherForecast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherLocalDataSource(context: Context) {

    companion object {
        private val TAG = WeatherLocalDataSource::class.java.simpleName
    }

    private val mWeatherDao = WeatherDatabase.getInstance(context).weatherDao()

    fun insert(weatherForecast: WeatherForecast) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.i(TAG, "insert - weatherForecast=$weatherForecast")
            mWeatherDao.insert(weatherForecast)
        }
    }

    fun getAllWeatherForecast(): List<WeatherForecast> {
        Log.d(TAG, "getAllWeatherForecast")
        return mWeatherDao.getAllWeatherForecast()
    }

    fun getLatestForecast(): WeatherForecast? {
        Log.d(TAG, "getLatestForecast")
        return mWeatherDao.getLatestForecast()
    }

    fun getLatestForecastLive(): LiveData<WeatherForecast?> {
        Log.d(TAG, "getLatestForecastLive")
        return mWeatherDao.getLatestForecastLive()
    }
}