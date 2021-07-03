package com.duyts.weatherapp.mock

import com.duyts.domain.exception.AppException
import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.entity.Main
import com.duyts.weather.domain.entity.WeatherForecast
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.usecase.GetCurrentWeather
import com.duyts.weather.domain.usecase.GetWeathersForecast

object MockData {
    private val weathersForecastResult = WeatherForecast(0, emptyList())
    private val currentWeatherResult = CurrentWeather(Main(),0,"",0)


    val getWeatherForecastParam = GetWeathersForecast.GetWeathersForecastParam(0, 10)
    val getWeathersForecastResult = ResponseHandler.Success(weathersForecastResult)

    val getCurrentWeatherParam = GetCurrentWeather.GetCurrentWeatherParam ("")
    val getCurrentWeatherResult = ResponseHandler.Success(currentWeatherResult)

    val failedResult = ResponseHandler.Failure(AppException.Unknown, "Failed mocked result")


}