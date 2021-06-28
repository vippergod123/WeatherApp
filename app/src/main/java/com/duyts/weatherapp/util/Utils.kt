package com.duyts.weatherapp.util

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun hasInternetConnection(application: Application): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }


    fun milisecondsToDate(time: Long): String {
        return milisecondsToDate(time,"EEE, dd-MM-yyyy")
    }

    fun secondToDate(time: Long): String {
        return milisecondsToDate(time* 1000)
    }

    fun secondToDate(time: Long, format: String): String {
        return milisecondsToDate(time* 1000, format)
    }

    @SuppressLint("SimpleDateFormat")
    fun milisecondsToDate(time:Long, format:String):String {
        val formatter = SimpleDateFormat(format)
        val calendar: Calendar = Calendar.getInstance()
        calendar.get(Calendar.YEAR)
        calendar.timeInMillis = time
        return formatter.format(calendar.time)
    }
}
