package com.duyts.weatherapp

import androidx.lifecycle.Observer
import com.duyts.weather.domain.entity.CurrentWeather
import com.duyts.weather.domain.entity.WeatherForecast
import com.duyts.weather.data.mock.MockData
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.usecase.GetCurrentWeather
import com.duyts.weather.domain.usecase.GetWeathersForecast
import com.duyts.weatherapp.util.Event
import com.duyts.weatherapp.viewmodel.MainViewModel
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MainViewModelTest : BaseViewModelTest() {
    private lateinit var viewModel: MainViewModel

    @Mock
    private lateinit var GetWeathersForecast: GetWeathersForecast

    @Captor
    private lateinit var weatherforecastCaptor: ArgumentCaptor<Event<ResponseHandler<WeatherForecast>>>

    @Mock
    private lateinit var weatherForecastLiveDataOb: Observer<Event<ResponseHandler<WeatherForecast>>>

    @Mock
    private lateinit var GetCurrentWeather: GetCurrentWeather

    @Mock
    private lateinit var currentWeatherLiveDataOb: Observer<Event<ResponseHandler<CurrentWeather>>>

    @Captor
    private lateinit var currentWeatherCaptor: ArgumentCaptor<Event<ResponseHandler<CurrentWeather>>>

    override fun setup() {
        super.setup()
        viewModel = MainViewModel(GetWeathersForecast, GetCurrentWeather)
        viewModel.currentWeather.observeForever(currentWeatherLiveDataOb)
        viewModel.weatherForecast.observeForever(weatherForecastLiveDataOb)
    }

    @Test
    fun getWeatherForecast_success() = testCoroutineRule.runBlockingTest {
        val mockGetWeatherForecastParam = MockData.getWeatherForecastParam
        `when`(GetWeathersForecast.invoke(mockGetWeatherForecastParam))
            .thenReturn(MockData.getWeathersForecastResult)

        runBlocking {
            viewModel.getWeatherForecast(mockGetWeatherForecastParam)
        }

        verify(GetWeathersForecast).invoke(anyType(GetWeathersForecast.GetWeathersForecastParam::class.java))
        verify(
            weatherForecastLiveDataOb,
            Mockito.times(2)
        ).onChanged(weatherforecastCaptor.capture())
//
        assertTrue(weatherforecastCaptor.allValues[0].peekContent() is ResponseHandler.Loading)
        assertTrue(weatherforecastCaptor.allValues[1].peekContent() is ResponseHandler.Success)
    }

    @Test
    fun getWeatherForecast_failed() = testCoroutineRule.runBlockingTest {
        val mockGetWeatherForecastParam = MockData.getWeatherForecastParam
        `when`(GetWeathersForecast.invoke(mockGetWeatherForecastParam))
            .thenReturn(MockData.failedResult)

        runBlocking {
            viewModel.getWeatherForecast(mockGetWeatherForecastParam)
        }

        verify(GetWeathersForecast).invoke(anyType(GetWeathersForecast.GetWeathersForecastParam::class.java))
        verify(
            weatherForecastLiveDataOb,
            Mockito.times(2)
        ).onChanged(weatherforecastCaptor.capture())
        assertTrue(weatherforecastCaptor.allValues[0].peekContent() is ResponseHandler.Loading)
        assertTrue(weatherforecastCaptor.allValues[1].peekContent() is ResponseHandler.Failure)
    }
}