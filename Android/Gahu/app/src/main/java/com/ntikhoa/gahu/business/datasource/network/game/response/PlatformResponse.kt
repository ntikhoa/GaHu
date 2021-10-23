package com.ntikhoa.gahu.business.datasource.network.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.domain.model.Platform

data class PlatformResponse(
    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("name")
    @Expose
    var name: String
) {
    fun toDomain(): Platform {
        return Platform(
            id = id,
            name = name
        )
    }
}