package com.duyts.weather.data.di

import androidx.room.Room
import com.duyts.weather.data.repository.WeatherRepositoryImpl
import com.duyts.weather.data.source.db.WeatherDatabase
import com.duyts.weather.domain.repository.WeatherRepository
import org.koin.core.module.Module
import org.koin.dsl.module


val dataModule = module {
    // TODO after release, add migration into DB
    single {
        Room.databaseBuilder(get(), WeatherDatabase::class.java, "Zavi-DB")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<WeatherDatabase>().provideRoomDao() }
    single<WeatherRepository> { WeatherRepositoryImpl(get(), get(), get()) }

}

fun getDataDI(): List<Module> {
    return listOf(dataModule, networkModule)
}