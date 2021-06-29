package com.duyts.weatherapp.db


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duyts.weatherapp.model.CurrentWeather
import com.duyts.weatherapp.model.WeatherForecast
import com.duyts.weatherapp.util.WeatherTypeConvertor

@Database(entities = [CurrentWeather::class, WeatherForecast::class], version = 1, exportSchema = false)
@TypeConverters(WeatherTypeConvertor::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): DAOAccess

    companion object {

        @Volatile
        private var INSTANCE: WeatherDatabase? = null

        fun getDataseClient(context: Context): WeatherDatabase {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {

                INSTANCE = Room
                    .databaseBuilder(context, WeatherDatabase::class.java, "current_weather_table")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }
        }

    }

}