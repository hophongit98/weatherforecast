package com.example.weatherapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.database.dao.WeatherDao
import com.example.weatherapp.model.WeatherForecast

@Database(entities = [WeatherForecast::class], version = 1, exportSchema = false)
@TypeConverters(WeatherConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        private const val DATABASE_NAME = "weatherDatabase.db"

        @Volatile
        private var instance: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase {
            instance?.let {
                return it
            }

            return synchronized(this) {
                var newInstance = instance
                if (newInstance != null) {
                    newInstance
                } else {
                    newInstance = Room.databaseBuilder(context, WeatherDatabase::class.java, DATABASE_NAME)
                            .build()

                    instance = newInstance
                    newInstance
                }
            }
        }
    }
}