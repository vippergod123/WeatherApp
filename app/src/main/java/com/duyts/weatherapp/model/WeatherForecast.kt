package com.duyts.weatherapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.duyts.weatherapp.util.WeatherTypeConvertor


@Entity(tableName = "weather_forecast_table")
data class WeatherForecast(
    @PrimaryKey
    var id: Int,
    @TypeConverters(WeatherTypeConvertor::class)
    val list: List<Weather>
)
