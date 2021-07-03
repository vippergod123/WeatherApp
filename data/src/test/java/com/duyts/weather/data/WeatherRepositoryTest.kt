package com.duyts.weather.data

import com.duyts.weather.data.mock.MockData
import com.duyts.weather.data.model.request.WeatherForecastResponse
import com.duyts.weather.data.repository.WeatherRepositoryImpl
import com.duyts.weather.data.source.WeatherApi
import com.duyts.weather.data.source.db.WeatherDAO
import com.duyts.weather.domain.repository.ResponseHandler
import com.duyts.weather.domain.repository.WeatherRepository
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import retrofit2.Response

class WeatherRepositoryTest : BaseRepositoryTest() {
    private lateinit var weatherRepository: WeatherRepository

    @Mock
    private lateinit var weatherDAO: WeatherDAO

    @Mock
    private lateinit var weatherApi: WeatherApi

    override fun setUp() {
        super.setUp()
        weatherRepository = WeatherRepositoryImpl(fakeContext, weatherApi, weatherDAO)
    }

    @Test
    fun `test getWeathersForecast already cached success`() = runBlocking {
        val param = MockData.getWeatherForecastParam
        val mockResult = MockData.getWeathersForecastResult
        whenever(weatherDAO.getWeathersForecast(param.id)).thenReturn(mockResult.data)
        val result = weatherRepository.getWeatherForecast(param.id, param.count)

        verify(weatherDAO, times(1)).getWeathersForecast(param.id)
        verifyZeroInteractions(weatherApi)

        assert((result as? ResponseHandler.Success)?.data == mockResult.data)

        resetMock()
    }
}