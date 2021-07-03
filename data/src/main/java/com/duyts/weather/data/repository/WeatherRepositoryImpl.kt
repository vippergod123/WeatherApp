package com.duyts.weather.data.repository

import android.content.Context
import android.util.Log
import com.duyts.domain.exception.AppException
import com.duyts.weather.data.C
import com.duyts.weather.data.model.request.WeatherForecastResponse
import com.duyts.weather.data.model.toDomainEntity
import com.duyts.weather.data.source.WeatherApi
import com.duyts.weather.data.source.db.WeatherDAO
import com.duyts.weather.data.source.wrapApiCall
import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.entity.WeatherForecast
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.repository.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl(
    private val context: Context,
    private val weatherApi: WeatherApi,
    private val weatherDAO: WeatherDAO
) :
    WeatherRepository {
    override suspend fun getWeatherForecast(
        locationId: Int,
        count: Int
    ): ResponseHandler<WeatherForecast> =
        withContext(Dispatchers.IO) {
            weatherDAO.getWeathersForecast(locationId)?.let {
                return@withContext ResponseHandler.Success(it)
            }

            val response: ResponseHandler<WeatherForecastResponse> = wrapApiCall {
                weatherApi.getWeatherForecast(
                    mapOf(
                        "id" to locationId.toString(),
                        "cnt" to count.toString(),
                        "appid" to C.appId
                    )
                )
            }

            return@withContext when (response) {
                is ResponseHandler.Success ->  {
                    val result: WeatherForecast = response.data.toDomainEntity(locationId)
                    weatherDAO.insert(result)
                    ResponseHandler.Success(result)
                }
                is ResponseHandler.Failure -> ResponseHandler.Failure(
                    response.error,
                    response.extra
                )
                else -> ResponseHandler.Failure(AppException.Failed)
            }
        }

    override suspend fun getCurrentWeather(location: String): ResponseHandler<CurrentWeather> =
        withContext(Dispatchers.IO) {
            val response = wrapApiCall {
                weatherApi.getCurrentWeather(mapOf("q" to location, "appid" to C.appId))
            }

            return@withContext when (response) {
                is ResponseHandler.Success -> ResponseHandler.Success(response.data.toDomainEntity())
                is ResponseHandler.Failure -> ResponseHandler.Failure(
                    response.error,
                    response.extra
                )
                else -> ResponseHandler.Failure(AppException.Failed)
            }
        }


}