package com.duyts.weather.data.source

import com.duyts.weather.data.model.request.CurrentWeatherResponse
import com.duyts.weather.data.model.request.WeatherForecastResponse
import com.duyts.weather.domain.repository.ResponseHandler
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface WeatherApi {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeatherForecast(@QueryMap(encoded = true) toMap: Map<String, String>): Response<WeatherForecastResponse>

    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@QueryMap(encoded = true) toMap: Map<String, String>): Response<CurrentWeatherResponse>
}