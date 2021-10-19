package com.ntikhoa.gahu.presentation.account

import com.ntikhoa.gahu.business.domain.model.Account

data class AccountState(
    val isLoading: Boolean = false,
    val message: String? = null,
    val account: Account? = null
) {
}