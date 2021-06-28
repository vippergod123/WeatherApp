package com.duyts.weatherapp.repository

import com.duyts.weatherapp.network.AppRetrofit
import com.duyts.weatherapp.network.WeatherRequest
import com.duyts.weatherapp.network.toMap

class AppRepository {
    suspend fun getProfile(params:WeatherRequest.GetParam) = AppRetrofit.weather.getWeather(params.toMap())
}