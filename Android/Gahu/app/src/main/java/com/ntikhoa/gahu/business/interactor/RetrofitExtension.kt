package com.ntikhoa.gahu.business.interactor

import com.ntikhoa.gahu.business.domain.util.Constants
import com.ntikhoa.gahu.business.domain.util.DataState
import retrofit2.HttpException
import java.lang.Exception

fun <T> handleUseCaseException(e: Throwable): DataState<T> {
    e.printStackTrace()
    when (e) {
        is HttpException -> { //Retrofit Exception
            val errorResponse = convertErrorBody(e)
            return DataState.error(errorResponse)
        }
        else -> {
            return DataState.error(e.message ?: Constants.UNKNOWN_ERROR)
        }
    }
}

private fun convertErrorBody(throwable: HttpException): String {
    return try {
        throwable.response()?.errorBody()?.string()
            ?: Constants.UNKNOWN_ERROR
    } catch (exception: Exception) {
        Constants.UNKNOWN_ERROR
    }
}
