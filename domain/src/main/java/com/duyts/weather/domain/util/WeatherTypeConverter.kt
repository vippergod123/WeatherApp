package com.duyts.weather.domain.util

import androidx.room.TypeConverter
import com.duyts.weather.domain.entity.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


class WeatherTypeConvertor {
    private val gson = Gson()

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

}