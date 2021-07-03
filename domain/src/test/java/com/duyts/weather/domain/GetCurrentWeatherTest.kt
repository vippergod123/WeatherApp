package com.duyts.weather.domain

import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.repository.WeatherRepository
import com.duyts.weather.domain.usecase.GetCurrentWeather
import com.duyts.weather.domain.mock.MockData
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify

class GetCurrentWeatherTest:BaseDomainTest() {
    private lateinit var getCurrentWeather: GetCurrentWeather

    @Mock
    private lateinit var weatherRepository: WeatherRepository


    override fun setUp() {
        super.setUp()
        getCurrentWeather = GetCurrentWeather(weatherRepository)
    }


    @Test
    fun test_getCurrentWeatherSuccess() = runBlocking {
        val param = MockData.getCurrentWeatherParam
        val mockResult = MockData.getCurrentWeatherResult
        whenever(weatherRepository.getCurrentWeather(param.location)).thenReturn(mockResult)

        val result = getCurrentWeather(param)

        Mockito.verify(weatherRepository, times(1)).getCurrentWeather(param.location)
        verifyNoMoreInteractions(weatherRepository)
        assert((result as? ResponseHandler.Success)?.data == mockResult.data)
    }

    @Test
    fun test_getCurrentWeatherFailed() = runBlocking {
        val param = MockData.getCurrentWeatherParam
        val mockResult = MockData.failedResult
        whenever(weatherRepository.getCurrentWeather(param.location)).thenReturn(mockResult)

        val result = getCurrentWeather(param)

        verify(weatherRepository, times(1)).getCurrentWeather(param.location)
        verifyNoMoreInteractions(weatherRepository)
        assert(result is ResponseHandler.Failure)
    }
}