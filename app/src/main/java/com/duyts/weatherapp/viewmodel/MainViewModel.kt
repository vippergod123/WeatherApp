package com.duyts.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.weatherapp.model.CurrentWeatherResponse
import com.duyts.weatherapp.model.WeatherForecastResponse
import com.duyts.weatherapp.network.WeatherRequest
import com.duyts.weatherapp.repository.AppRepository
import com.duyts.weatherapp.util.Event
import com.duyts.weatherapp.util.Resource
import com.duyts.weatherapp.util.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class MainViewModel(private val app: Application, private val appRepository: AppRepository) :
    ViewModel() {

    private val _weatherForecast = MutableLiveData<Event<Resource<WeatherForecastResponse>>>()
    val weatherForecast: LiveData<Event<Resource<WeatherForecastResponse>>> =
        _weatherForecast

    private val _currentWeather = MutableLiveData<Event<Resource<CurrentWeatherResponse>>>()
    val currentWeather: LiveData<Event<Resource<CurrentWeatherResponse>>> =
        _currentWeather

    fun getWeatherForecast(location: String, count: Int) = viewModelScope.launch {
        getWeatherInternal(location, count)
    }

    fun getCurrentWeather(location: String) = viewModelScope.launch {
        getCurrentWeatherInternal(location)
    }

    private suspend fun getCurrentWeatherInternal(location: String) {
        _currentWeather.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(app)) {
                val response =
                    appRepository.getCurrentWeather(WeatherRequest.GetCurrentParam(location))
//                        _profile.postValue(handlePicsResponse(response))
                _currentWeather.postValue(handleResponse(response))
            } else {
                _currentWeather.postValue(Event(Resource.Failed("No internet connection")))
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            when (t) {
                is IOException -> {
                    _currentWeather.postValue(Event(Resource.Failed("Network error")))
                }
                else -> {
                    _currentWeather.postValue(Event(Resource.Failed("Network error")))
                }
            }
        }
    }


    private suspend fun getWeatherInternal(location: String, count: Int) {
        _weatherForecast.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(app)) {
                val response = appRepository.getWeatherForecast(
                    WeatherRequest.GetForecastParam(
                        location,
                        count
                    )
                )
//                        _profile.postValue(handlePicsResponse(response))
                _weatherForecast.postValue(handleResponse(response))
            } else {
                _weatherForecast.postValue(Event(Resource.Failed("No internet connection")))
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            when (t) {
                is IOException -> {
                    _weatherForecast.postValue(Event(Resource.Failed("Network error")))
                }
                else -> {
                    _weatherForecast.postValue(Event(Resource.Failed("Network error")))
                }
            }
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
}