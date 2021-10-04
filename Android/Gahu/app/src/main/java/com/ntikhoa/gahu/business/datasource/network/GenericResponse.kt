package com.ntikhoa.gahu.business.datasource.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenericResponse<T>(
    @SerializedName("data")
    @Expose
    var data: T? = null,

    @SerializedName("message")
    @Expose
    var message: String
)