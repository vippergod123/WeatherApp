package com.duyts.domain.exception

import java.lang.Exception

sealed class AppException : Exception() {
    object Failed : AppException()
    object InvalidParam : AppException()
    object NoNetwork: AppException()
    object UnsolvedHost: AppException()
    object NotFound: AppException()

    object Unknown: AppException()
    companion object {
        fun classifyException(code: Int): AppException {
            return when (code) {
                -100 -> Failed
                -101 -> InvalidParam
                -102 -> NoNetwork
                -103 -> UnsolvedHost
                -104 -> NotFound
                else -> Unknown
            }
        }
    }
}