package com.ntikhoa.gahu.business.datasource.network.auth.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.datasource.cache.account.AccountEntity
import com.ntikhoa.gahu.business.domain.model.Account

data class AuthResponse(
    @SerializedName("token")
    @Expose
    var token: String,

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("email")
    @Expose
    var email: String,

    @SerializedName("username")
    @Expose
    var username: String
) {
    fun toDomain(): Account {
        return Account(
            id = id,
            token = token,
            email = email,
            username = username
        )
    }
}