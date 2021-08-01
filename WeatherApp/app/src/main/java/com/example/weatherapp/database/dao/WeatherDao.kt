package com.example.weatherapp.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.model.WeatherForecast

@Dao
abstract class WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(weatherForecast: WeatherForecast)

    @Query("SELECT COUNT(*) FROM ${WeatherForecast.TABLE_NAME}")
    abstract fun getNumOfWeatherForecast(): Int

    @Query("DELETE FROM ${WeatherForecast.TABLE_NAME} WHERE id IN (SELECT id FROM ${WeatherForecast.TABLE_NAME} ORDER BY createAt ASC LIMIT 1)")
    abstract fun deleteFirstWeatherForecast()

    @Query("SELECT * FROM ${WeatherForecast.TABLE_NAME} WHERE id = :id")
    abstract fun getDailyForecastById(id: Int): WeatherForecast?

    @Query("SELECT * FROM ${WeatherForecast.TABLE_NAME}")
    abstract fun getAllWeatherForecast(): List<WeatherForecast>

    @Query("SELECT * FROM ${WeatherForecast.TABLE_NAME} ORDER BY createAt DESC LIMIT 1")
    abstract fun getLatestForecast(): WeatherForecast?

    @Query("SELECT * FROM ${WeatherForecast.TABLE_NAME} ORDER BY createAt DESC LIMIT 1")
    abstract fun getLatestForecastLive(): LiveData<WeatherForecast?>


}