package com.duyts.weatherapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.duyts.weatherapp.model.WeatherForecast

@Dao
interface DAOAccess {

//    @Insert(onConflict = OnConflictStrategy.REPLACE)

    @Insert
    suspend fun insert(item: WeatherForecast)


    @Query("SELECT * FROM weather_forecast_table WHERE id=:id")
    suspend fun getWeathersForecast(id: Int): WeatherForecast?

}