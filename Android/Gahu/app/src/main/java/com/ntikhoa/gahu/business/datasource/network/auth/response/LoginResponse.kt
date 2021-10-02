package com.ntikhoa.gahu.business.datasource.network.auth.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse(
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
)