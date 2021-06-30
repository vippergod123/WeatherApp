package com.duyts.weather.domain.repository

import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.entity.WeatherForecast

interface WeatherRepository {
    suspend fun getWeatherForecast(location: Int, count: Int): ResponseHandler<WeatherForecast>
    suspend fun getCurrentWeather(location:String): ResponseHandler<CurrentWeather>
}