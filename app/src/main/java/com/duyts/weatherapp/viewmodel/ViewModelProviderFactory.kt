package com.duyts.weatherapp.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.duyts.weatherapp.repository.AppRepository
import com.duyts.weatherapp.db.RoomDatabase
import com.duyts.weatherapp.repository.RoomRepository

class ViewModelProviderFactory(
    val app: Application,
    val appRepository: AppRepository,
    val roomRepository: RoomRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(app, appRepository,roomRepository) as T
        }

        throw IllegalArgumentException("Unknown class name")
    }

}