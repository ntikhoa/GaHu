package com.ntikhoa.gahu.presentation.auth.login

sealed class LoginEvent {
    data class Login(
        val email: String,
        val password: String
    ) : LoginEvent()
}