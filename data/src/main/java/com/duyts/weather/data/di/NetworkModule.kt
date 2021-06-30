package com.duyts.weather.data.di

import android.content.Context
import com.duyts.weather.data.BuildConfig
import com.duyts.weather.data.di.Constant.WEATHER
import com.duyts.weather.data.di.Constant.WEATHER_DOMAIN_API
import com.duyts.weather.data.source.WeatherApi
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


private object Constant {
    val WEATHER_DOMAIN_API= "WEATHER_DOMAIN_API"
    val WEATHER= "WEATHER"
    val CONNECTION_TIME_OUT_SECOND = 5L
}

val networkModule = module {

    single { provideGson() }
    single { provideClient(get()) }

    factory(named(WEATHER_DOMAIN_API)) { provideApiDomain() }
    factory(named(WEATHER)) { provideRetrofit(
        get(named(WEATHER_DOMAIN_API)),
        get(),
        get()
    ) }
    factory { createService(get(named(WEATHER)), WeatherApi::class.java) }
}


fun provideApiDomain(): String {
    return "https://api.openweathermap.org/"
}

inline fun <reified T> createService(retrofit: Retrofit, apiService: Class<T>): T {
    return retrofit.create(apiService)
}

fun provideRetrofit(baseUrl: String, client: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(client)
        .build()
}

fun provideClient(context: Context): OkHttpClient {
    val builder = OkHttpClient.Builder().callTimeout(Constant.CONNECTION_TIME_OUT_SECOND, TimeUnit.SECONDS)
        .readTimeout(Constant.CONNECTION_TIME_OUT_SECOND, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        builder.also {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            it.addInterceptor(logger)
        }
    }
    return builder.build()
}

fun provideGson(): Gson {

    return Gson()
}
