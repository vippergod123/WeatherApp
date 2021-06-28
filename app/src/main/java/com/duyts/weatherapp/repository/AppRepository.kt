package com.duyts.weatherapp.repository

import com.duyts.weatherapp.network.AppRetrofit
import com.duyts.weatherapp.network.WeatherRequest
import com.duyts.weatherapp.network.toMap

class AppRepository {
    suspend fun getWeatherForecast(params:WeatherRequest.GetForecastParam) = AppRetrofit.weather.getWeatherForecast(params.toMap())

    suspend fun getCurrentWeather(params:WeatherRequest.GetCurrentParam) = AppRetrofit.weather.getCurrentWeather(params.toMap())
}