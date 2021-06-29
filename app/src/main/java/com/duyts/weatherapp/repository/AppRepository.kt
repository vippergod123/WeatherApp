package com.duyts.weatherapp.repository

import android.util.Log
import com.duyts.weatherapp.model.WeatherForecast
import com.duyts.weatherapp.network.AppRetrofit
import com.duyts.weatherapp.network.WeatherRequest
import com.duyts.weatherapp.network.toMap
import com.duyts.weatherapp.util.Event
import com.duyts.weatherapp.util.Resource
import retrofit2.Response

class AppRepository(private val roomRepository: RoomRepository) {


    suspend fun getCurrentWeather(params: WeatherRequest.GetCurrentParam) =
        AppRetrofit.weather.getCurrentWeather(params.toMap())


    suspend fun getWeatherForecast(params: WeatherRequest.GetForecastParam): Resource<WeatherForecast> {
        val weather = roomRepository.getWeatherForecastRoom(params.id)
        return if (weather?.list?.isNotEmpty() == true) {
            Log.d("CHIRST", "ALREADY CACHE");
            Resource.Success(weather)
        } else {
            val response = AppRetrofit.weather.getWeatherForecast(params.toMap())
            val data = handleResponse(response).peekContent().data
            data?.let {
                roomRepository.saveWeatherForecastRoom(WeatherForecast(params.id, data.list))
                return Resource.Success(WeatherForecast(params.id, data.list))
            }
            return Resource.Failed("Cannot insert database")
        }
    }


    private fun <T> handleResponse(response: Response<T>): Event<Resource<T>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Failed(response.message()))
    }

    private fun <T> handleData(data: T): Event<Resource<T>> {
        return Event(Resource.Success(data))
    }
}

