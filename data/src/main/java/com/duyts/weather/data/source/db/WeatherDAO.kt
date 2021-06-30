package com.duyts.weather.data.source.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duyts.weather.domain.entity.WeatherForecast

@Dao
interface WeatherDAO {


    @Insert
    suspend fun insert(item: WeatherForecast)


    @Query("SELECT * FROM weather_forecast_table WHERE id=:id")
    suspend fun getWeathersForecast(id: Int): WeatherForecast?
}