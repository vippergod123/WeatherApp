package com.duyts.weather.data.source

import android.content.Context
import com.duyts.domain.exception.AppException
import com.duyts.weather.domain.repository.ResponseHandler
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException


/**
 * For encrypt api call
 */
suspend inline fun <reified T> wrapApiCall(
    crossinline api: suspend () -> Response<T>
): ResponseHandler<T> {
    return try {
        val response = withContext(Dispatchers.IO) {
            api()
        }
        when(response.code()) {
            200 -> {
                ResponseHandler.Success(response.body()!!)
            }
            else -> {
                ResponseHandler.Failure(AppException.Unknown)
            }
        }

//        when (response.err) {
//            0 -> {
//                if (response.data.isNullOrEmpty()) return ResponseHandler.Complete
////                val result = convertFromJson(raw!!, T::class.java)
//                ResponseHandler.Success(result!!)
//            }
//            else -> {
//                val exception = AppException.classifyException(response.err)
//                ResponseHandler.Failure(exception) // extra for case of captcha
//            }
//        }
    } catch (exp: Exception) {
        return when (exp) {
            is HttpException, AppException.NoNetwork -> {
                ResponseHandler.Failure(AppException.NoNetwork)
            }
            is UnknownHostException -> {
                ResponseHandler.Failure(AppException.UnsolvedHost)
            }
            else -> {
                ResponseHandler.Failure(exp)
            }
        }
    }
}


fun <T> convertFromJson(json: String, clazz: Class<T>): T {
    val gson = Gson()
    return gson.fromJson(json, clazz)
}