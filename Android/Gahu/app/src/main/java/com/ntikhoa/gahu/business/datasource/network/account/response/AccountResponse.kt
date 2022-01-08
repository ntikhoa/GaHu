package com.ntikhoa.gahu.business.datasource.network.account.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.domain.model.Account
import com.ntikhoa.gahu.business.domain.model.Author

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
    fun toAccountDomain(token: String): Account {
        return Account(
            id = id,
            token = token,
            email = email,
            username = username
        )
    }

    fun toAuthorDomain(): Author {
        return Author(
            id = id,
            email = email,
            username = username
        )
    }
}