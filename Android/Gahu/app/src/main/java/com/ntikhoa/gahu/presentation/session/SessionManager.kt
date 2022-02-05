package com.ntikhoa.gahu.presentation.session

data class SessionManager(
    var token: String? = null,
    var userId: String? = null
) {
    fun login(token: String, userId: String) {
        this.token = token
        this.userId = userId
    }

    fun logout() {
        token = null
        userId = null
    }
}