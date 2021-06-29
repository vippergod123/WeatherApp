package com.duyts.weatherapp.repository

import com.duyts.weatherapp.db.RoomDatabase
import com.duyts.weatherapp.model.WeatherForecast

class RoomRepository {
    suspend fun getWeatherForecastRoom(id:Int) = RoomDatabase.weather.weatherDao().getWeathersForecast(id)
    suspend fun saveWeatherForecastRoom( item: WeatherForecast) =
        RoomDatabase.weather.weatherDao().insert(item)
}