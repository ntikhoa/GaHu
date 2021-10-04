package com.ntikhoa.gahu.business.domain.model

import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity

data class Account(
    var id: String,
    var token: String,
    var email: String,
    var username: String
) {
    fun toEntity(): AccountEntity {
        return AccountEntity(
            pk = id,
            token = token,
            email = email,
            username = username
        )
    }
}