package com.duyts.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.duyts.weatherapp.MainApplication
import com.duyts.weatherapp.model.WeatherResponse
import com.duyts.weatherapp.network.WeatherRequest
import com.duyts.weatherapp.repository.AppRepository
import com.duyts.weatherapp.util.Event
import com.duyts.weatherapp.util.Resource
import com.duyts.weatherapp.util.Utils
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class MainViewModel(private val app: Application, private val appRepository: AppRepository): ViewModel() {

    private val _weather = MutableLiveData<Event<Resource<WeatherResponse>>>()
    val weather: LiveData<Event<Resource<WeatherResponse>>> = _weather

    fun getWeather(location:String, count:Int) = viewModelScope.launch {
        getWeatherInternal(location, count)
    }


    private suspend fun getWeatherInternal(location: String, count: Int) {
        _weather.postValue(Event(Resource.Loading()))
        try {
            if (Utils.hasInternetConnection(app)) {
                val response = appRepository.getProfile(WeatherRequest.GetParam(location, count))
//                        _profile.postValue(handlePicsResponse(response))
                _weather.postValue(handleResponse(response))
            } else {
                _weather.postValue(Event(Resource.Failed("No internet connection")))
            }
        } catch (t: Throwable) {
            t.printStackTrace()
            when (t) {
                is IOException -> {
                    _weather.postValue(Event(Resource.Failed("Network error")))
                }
                else -> {
                    _weather.postValue(Event(Resource.Failed("Network error")))
                }
            }
        }
    }


    private fun handleResponse(response: Response<WeatherResponse>): Event<Resource<WeatherResponse>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Event(Resource.Success(resultResponse))
            }
        }
        return Event(Resource.Failed(response.message()))
    }
}