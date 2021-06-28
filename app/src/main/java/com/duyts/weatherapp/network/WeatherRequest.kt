package com.duyts.weatherapp.network

import com.duyts.weatherapp.Constant

class WeatherRequest {
    data class GetParam(val location: String, val count: Int)
}

fun WeatherRequest.GetParam.toMap(): Map<String, String> {
    return mapOf(
        "q" to location.lowercase(),
        "cnt" to count.toString(),
        "appid" to Constant.appId
    )
}