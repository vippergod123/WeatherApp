package com.duyts.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.domain.exception.AppException
import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.entity.WeatherForecast
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.usecase.GetCurrentWeather
import com.duyts.weather.domain.usecase.GetWeathersForecast
import com.duyts.weatherapp.util.Event
import kotlinx.coroutines.launch


class MainViewModel(
    private val app: Application,
    private val GetWeathersForecast: GetWeathersForecast,
    private val GetCurrentWeather: GetCurrentWeather
) :
    ViewModel() {

    private val _weatherForecast = MutableLiveData<Event<ResponseHandler<WeatherForecast>>>()
    val weatherForecast: LiveData<Event<ResponseHandler<WeatherForecast>>> =
        _weatherForecast

    private val _currentWeather = MutableLiveData<Event<ResponseHandler<CurrentWeather>>>()
    val currentWeather: LiveData<Event<ResponseHandler<CurrentWeather>>> =
        _currentWeather

    fun getWeatherForecast(id: Int, count: Int) = viewModelScope.launch {
        _weatherForecast.value = Event(ResponseHandler.Loading)

        val param =
            com.duyts.weather.domain.usecase.GetWeathersForecast.GetWeathersForecastParam(id, count)
        when (val result = GetWeathersForecast.invoke(param)) {
            is ResponseHandler.Success -> _weatherForecast.value =
                Event(ResponseHandler.Success(result.data))
            is ResponseHandler.Failure -> _weatherForecast.value = Event(result)
            else -> _weatherForecast.value = Event(ResponseHandler.Failure(AppException.Failed))
        }
    }

    fun getCurrentWeather(location: String) = viewModelScope.launch {
        _currentWeather.value = Event(ResponseHandler.Loading)

        val param =
            com.duyts.weather.domain.usecase.GetCurrentWeather.GetCurrentWeatherParam(location)
        when (val result = GetCurrentWeather.invoke(param)) {
            is ResponseHandler.Success -> _currentWeather.value =
                Event(ResponseHandler.Success(result.data))
            is ResponseHandler.Failure -> _currentWeather.value = Event(result)
            else -> _currentWeather.value = Event(ResponseHandler.Failure(AppException.Failed))
        }
    }


}