package com.duyts.weatherapp.network

import com.duyts.weatherapp.model.CurrentWeatherResponse
import com.duyts.weatherapp.model.WeatherForecastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Endpoint {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherForecast(@QueryMap(encoded = true) toMap: Map<String, String>): Response<WeatherForecastResponse>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@QueryMap(encoded = true) toMap: Map<String, String>): Response<CurrentWeatherResponse>
}