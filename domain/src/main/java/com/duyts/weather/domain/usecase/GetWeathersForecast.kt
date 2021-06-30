package com.duyts.weather.domain.usecase

import com.duyts.weather.domain.entity.WeatherForecast
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.repository.WeatherRepository

class GetWeathersForecast(private val weatherRepository: WeatherRepository) :
    BaseUsecase<GetWeathersForecast.GetWeathersForecastParam, ResponseHandler<WeatherForecast>>() {


    data class GetWeathersForecastParam(
        val id: Int,
        val count: Int,
    )

    override suspend fun invoke(param: GetWeathersForecastParam): ResponseHandler<WeatherForecast> {
        return weatherRepository.getWeatherForecast(param.id, param.count)
    }
}