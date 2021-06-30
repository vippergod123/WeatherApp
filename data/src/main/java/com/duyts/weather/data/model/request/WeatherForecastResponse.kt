package com.duyts.weather.data.model.request

import com.duyts.weather.domain.entity.Weather
import com.google.gson.annotations.SerializedName


data class WeatherForecastResponse(
    @field:SerializedName("city")
    val city: City ,

    @field:SerializedName("cnt")
    val cnt: Int? = null,

    @field:SerializedName("cod")
    val cod: String? = null,

    @field:SerializedName("message")
    val message: Double? = null,

    @field:SerializedName("list")
    val list: List<Weather>
)

data class City(

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("coord")
    val coord: Coord? = null,

    @field:SerializedName("timezone")
    val timezone: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("population")
    val population: Int? = null
)


data class Coord(

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)
