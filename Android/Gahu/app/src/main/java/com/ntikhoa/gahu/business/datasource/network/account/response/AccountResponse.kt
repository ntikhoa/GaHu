package com.ntikhoa.gahu.business.datasource.network.account.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.domain.model.Account

data class AccountResponse(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("username")
    @Expose
    var username: String,

    @SerializedName("email")
    @Expose
    var email: String
) {
    fun toDomain(token: String): Account {
        return Account(
            id = id,
            token = token,
            email = email,
            username = username
        )
    }
}