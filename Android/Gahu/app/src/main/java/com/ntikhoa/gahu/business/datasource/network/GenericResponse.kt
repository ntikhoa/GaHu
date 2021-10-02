package com.ntikhoa.gahu.business.datasource.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("status")
    @Expose
    var status: Int,

    @SerializedName("data")
    @Expose
    var data: T? = null,

    @SerializedName("error")
    @Expose
    var error: String? = null,

    @SerializedName("message")
    @Expose
    var message: String
)