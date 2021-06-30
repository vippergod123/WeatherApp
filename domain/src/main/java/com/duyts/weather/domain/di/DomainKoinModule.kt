package com.duyts.weather.domain.di

import com.duyts.weather.domain.usecase.GetCurrentWeather
import com.duyts.weather.domain.usecase.GetWeathersForecast
import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule = module {
    single { GetWeathersForecast(get()) }
    single { GetCurrentWeather(get()) }
}

fun getDomainDI(): List<Module> {
    return listOf(domainModule)
}