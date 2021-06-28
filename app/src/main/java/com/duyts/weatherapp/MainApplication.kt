package com.duyts.weatherapp

import android.app.Application

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        application = this

    }

    companion object {
        private lateinit var application: Application
        fun getApplication(): Application {
            return application
        }
    }
}