package com.duyts.weatherapp.db

import com.duyts.weatherapp.MainApplication

class RoomDatabase {
    companion object {

        private val weatherDatabase by lazy {
            WeatherDatabase.getDataseClient(MainApplication.getApplication())
        }

        val weather by lazy {
            WeatherDatabase.getDataseClient(MainApplication.getApplication())
        }

    }
}