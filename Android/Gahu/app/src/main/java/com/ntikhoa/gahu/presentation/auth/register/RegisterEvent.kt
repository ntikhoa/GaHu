package com.ntikhoa.gahu.presentation.auth.register

sealed class RegisterEvent {
    data class Register(
        val email: String,
        val username: String,
        val password: String,
        val confirmPassword: String
    ) : RegisterEvent()
}