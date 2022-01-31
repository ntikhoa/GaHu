package com.ntikhoa.gahu.business.datasource.network.trophy.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.domain.model.Trophy
import com.ntikhoa.gahu.business.domain.model.TrophyGame

data class TrophyGameResponse(
    @SerializedName("bronze")
    @Expose
    val bronze: Int,

    @SerializedName("gold")
    @Expose
    val gold: Int,

    @SerializedName("got")
    @Expose
    val got: Int,

    @SerializedName("image")
    @Expose
    val image: String,

    @SerializedName("isPlat")
    @Expose
    val isPlat: Boolean,

    @SerializedName("silver")
    @Expose
    val silver: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("total")
    @Expose
    val total: Int
) {
    fun toDomain(): TrophyGame {
        return TrophyGame(
            title = title,
            imageUrl = image,
            got = got,
            trophy = Trophy(
                totalTrophies = total,
                platinum = if (isPlat) 1 else 0,  //One game only has one platinum trophy
                gold = gold,
                silver = silver,
                bronze = bronze
            )
        )
    }
}