package com.duyts.weather.domain.usecase

import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.repository.WeatherRepository


open class GetCurrentWeather(private val weatherRepository: WeatherRepository) :
    BaseUsecase<GetCurrentWeather.GetCurrentWeatherParam, ResponseHandler<CurrentWeather>>() {


    data class GetCurrentWeatherParam(
        val location: String,
    )

    override suspend fun invoke(param: GetCurrentWeatherParam): ResponseHandler<CurrentWeather> {
        return weatherRepository.getCurrentWeather(param.location)
    }
}