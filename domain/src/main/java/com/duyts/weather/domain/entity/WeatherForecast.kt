package com.duyts.weather.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.duyts.weather.domain.util.WeatherTypeConvertor


@Entity(tableName = "weather_forecast_table")
data class WeatherForecast(
    @PrimaryKey
    var id: Int,

    @TypeConverters(WeatherTypeConvertor::class)
    val list: List<Weather>
)
