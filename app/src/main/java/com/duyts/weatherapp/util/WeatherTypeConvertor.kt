package com.duyts.weatherapp.util

import androidx.room.TypeConverter
import com.duyts.weatherapp.model.City
import com.duyts.weatherapp.model.Weather
import com.duyts.weatherapp.model.WeatherForecastResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class WeatherTypeConvertor {
    private val gson = Gson()

    @TypeConverter
    fun jsonToCity(data: String): City {
        return gson.fromJson(data, City::class.java)
    }

    @TypeConverter
    fun cityToJson(cl: City): String {
        return gson.toJson(cl)
    }

    @TypeConverter
    fun jsonToWeather(data: String): Weather {
        return gson.fromJson(data, Weather::class.java)
    }

    @TypeConverter
    fun weatherToJson(cl: Weather): String {
        return gson.toJson(cl)
    }


    @TypeConverter
    fun jsonToListWeather(data: String?): List<Weather> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Weather>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listWeatherToJson(someObjects: List<Weather>): String {
        return gson.toJson(someObjects)
    }

//    @TypeConverter
//    fun<T> fromObjToJson(obj: T): String {
//        return gson.toJson(obj)
//    }
//
//    @TypeConverter
//    fun<T> fromJsonToObj(js: String): T {
//        val type = object : TypeToken<List<T>>() {}.type
//        return gson.fromJson(js,type)
//    }

//    @TypeConverter
//    fun jsonToWeatherForecast(data: String): WeatherForecastResponse {
//        return gson.fromJson(data, WeatherForecastResponse::class.java)
//    }
//
//    @TypeConverter
//    fun weatherForecastToJson(cl: WeatherForecastResponse): String {
//        return gson.toJson(cl)
//    }
}