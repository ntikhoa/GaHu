package com.ntikhoa.gahu.business.domain.util

data class DataState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val message: String? = null
) {
    companion object {
        fun <T> loading(): DataState<T> = DataState(isLoading = true)

        fun <T> error(
            message: String
        ): DataState<T> {
            return error(
                message = message
            )
        }


        fun <T> data(
            message: String?,
            data: T? = null
        ): DataState<T> {
            return DataState(
                message = message,
                data = data
            )
        }
    }
}