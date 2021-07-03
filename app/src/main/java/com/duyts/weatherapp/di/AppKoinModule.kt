package com.duyts.weatherapp.di

import com.duyts.weather.data.di.getDataDI
import com.duyts.weather.domain.di.getDomainDI
import com.duyts.weatherapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


val appKoin = module {
    viewModel { MainViewModel(get(), get()) }
}

fun getDI(): List<Module> {
    return mutableListOf(appKoin).also {
        it.addAll(getDomainDI())
        it.addAll(getDataDI())
    }
}