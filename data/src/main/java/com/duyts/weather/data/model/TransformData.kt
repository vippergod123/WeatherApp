package com.duyts.weather.data.model

import com.duyts.weather.data.model.request.CurrentWeatherResponse
import com.duyts.weather.data.model.request.WeatherForecastResponse
import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.entity.Main
import com.duyts.weather.domain.entity.WeatherForecast

fun WeatherForecastResponse.toDomainEntity(id:Int) = WeatherForecast(id, this.list)

fun CurrentWeatherResponse.toDomainEntity() = CurrentWeather(
    Main(this.main?.temp ?: 0.0),
    this.dt,
    this.name ?: "",
    this.id)