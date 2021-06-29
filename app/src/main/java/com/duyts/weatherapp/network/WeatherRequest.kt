package com.duyts.weatherapp.network

import com.duyts.weatherapp.Constant

class WeatherRequest {
    data class GetForecastParam(val id: Int, val count: Int)
    data class GetCurrentParam(val location: String)
}


fun WeatherRequest.GetCurrentParam.toMap(): Map<String, String> {
    return mapOf(
        "q" to location.lowercase(),
        "appid" to Constant.appId
    )
}


fun WeatherRequest.GetForecastParam.toMap(): Map<String, String> {
    return mapOf(
        "id" to id.toString(),
        "cnt" to count.toString(),
        "appid" to Constant.appId
    )
}