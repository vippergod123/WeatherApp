package com.duyts.weatherapp

import android.app.Application
import com.duyts.weatherapp.di.getDI
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        application = this

        startKoin {
            androidContext(this@MainApplication)
            modules(getDI())
            androidLogger(Level.DEBUG)
        }
    }

    companion object {
        private lateinit var application: Application
        fun getApplication(): Application {
            return application
        }
    }
}