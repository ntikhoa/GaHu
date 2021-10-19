package com.ntikhoa.gahu.business.domain.util

class ErrorHandler {
    companion object {
        const val EMAIL_NOT_EXIST = "Email does not exist"
        const val WRONG_PASSWORD = "Wrong password"
        const val EMAIL_ALREADY_EXIST = "Email already exist"
        const val DATASTORE_VALUE_NOT_FOUND = "value not found"
        const val CHECK_PREV_AUTH_FAILED = "Check previous auth failed"
        const val NETWORK_ERROR =
            "Unable to resolve host \"gahu.herokuapp.com\": No address associated with hostname"
    }
}