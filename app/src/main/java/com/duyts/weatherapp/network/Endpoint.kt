package com.duyts.weatherapp.network

import com.duyts.weatherapp.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Endpoint {
    @GET("data/2.5/forecast/daily")
    suspend fun getWeather(@QueryMap(encoded = true) toMap: Map<String, String>): Response<WeatherResponse>
}