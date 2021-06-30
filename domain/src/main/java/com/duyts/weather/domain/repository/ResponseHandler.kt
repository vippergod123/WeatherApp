package com.duyts.weather.domain.repository

import com.duyts.domain.exception.AppException
sealed class ResponseHandler<out T> {
    /* Success response with data */
    data class Success<out T>(val data: T) : ResponseHandler<T>()

    /* A Success response with no data */
    object Complete : ResponseHandler<Nothing>()

    /* Failed Response with an exception and message */
    data class Failure(val error: Throwable = AppException.Unknown, val extra: String = "") : ResponseHandler<Nothing>()

    /* Function had already called and now the previous one is waiting to execute */
    object Pending : ResponseHandler<Nothing>()

    /** Function had already called before and the previous one is executing */
    object Loading : ResponseHandler<Nothing>()
}