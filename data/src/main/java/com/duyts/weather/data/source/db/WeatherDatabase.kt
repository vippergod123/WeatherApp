package com.duyts.weather.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.duyts.weather.domain.entity.WeatherForecast
import com.duyts.weather.domain.util.WeatherTypeConvertor


@Database(entities = [WeatherForecast::class], version = 1, exportSchema = false)
@TypeConverters(value = [WeatherTypeConvertor::class])
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun provideRoomDao(): WeatherDAO
}