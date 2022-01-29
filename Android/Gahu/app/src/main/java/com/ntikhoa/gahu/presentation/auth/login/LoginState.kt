package com.ntikhoa.gahu.presentation.auth.login

data class LoginState(
    var isLoading: Boolean = false,
    var message: String? = null
)