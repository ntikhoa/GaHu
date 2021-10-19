package com.ntikhoa.gahu.presentation.account

sealed class AccountEvent {
    class Logout : AccountEvent()

    class GetAccount : AccountEvent()
}