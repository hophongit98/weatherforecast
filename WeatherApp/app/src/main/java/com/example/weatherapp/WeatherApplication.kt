package com.example.weatherapp

import android.app.Application

class WeatherApplication : Application() {

    companion object {
        lateinit var instance: WeatherApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}