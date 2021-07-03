package com.duyts.weather.domain

import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.repository.WeatherRepository
import com.duyts.weather.domain.usecase.GetWeathersForecast
import com.duyts.weather.domain.mock.MockData
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify

class GetWeatherForecastTest : BaseDomainTest() {
    private lateinit var getWeathersForecast: GetWeathersForecast

    @Mock
    private lateinit var weatherRepository: WeatherRepository


    override fun setUp() {
        super.setUp()
        getWeathersForecast = GetWeathersForecast(weatherRepository)
    }


    @Test
    fun test_getWeathersForecastSuccess() = runBlocking {
        val param = MockData.getWeatherForecastParam
        val mockResult = MockData.getWeathersForecastResult
        whenever(weatherRepository.getWeatherForecast(param.id, param.count)).thenReturn(mockResult)

        val result = getWeathersForecast(param)

        verify(weatherRepository, times(1)).getWeatherForecast(param.id, param.count)
        verifyNoMoreInteractions(weatherRepository)
        assert((result as? ResponseHandler.Success)?.data?.list == mockResult.data.list)
    }

    @Test
    fun test_getWeathersForecastFailed() = runBlocking {
        val param = MockData.getWeatherForecastParam
        val mockResult = MockData.failedResult
        whenever(weatherRepository.getWeatherForecast(param.id, param.count)).thenReturn(mockResult)

        val result = getWeathersForecast(param)

        verify(weatherRepository, times(1)).getWeatherForecast(param.id, param.count)
        verifyNoMoreInteractions(weatherRepository)
        assert(result is ResponseHandler.Failure)
    }
}