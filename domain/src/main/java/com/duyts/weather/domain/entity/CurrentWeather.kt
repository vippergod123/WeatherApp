package com.duyts.weather.domain.entity


data class CurrentWeather(
    val main: Main,
    val dt: Int = 0,
//    val weather: List<CurrentWeatherItem>,
    val name: String,
    val id: Int,
)

//
//data class CurrentWeatherItem(
//    val icon: String,
//
//    val description: String,
//
//    val main: String,
//
//    val id: Int
//)


data class Main(
    val temp: Double = 0.0,
)
