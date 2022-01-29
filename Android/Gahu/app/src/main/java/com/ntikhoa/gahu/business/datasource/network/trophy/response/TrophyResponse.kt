package com.ntikhoa.gahu.business.datasource.network.trophy.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.domain.model.Trophy

data class TrophyResponse(

    @SerializedName("avatar")
    @Expose
    val avatar: String,

    @SerializedName("username")
    @Expose
    val username: String,

    @SerializedName("bronze")
    @Expose
    val bronze: Int,

    @SerializedName("completion")
    @Expose
    val completion: Double,

    @SerializedName("gamesPlayed")
    @Expose
    val gamesPlayed: Int,

    @SerializedName("gold")
    @Expose
    val gold: Int,

    @SerializedName("platinum")
    @Expose
    val platinum: Int,

    @SerializedName("silver")
    @Expose
    val silver: Int,

    @SerializedName("totalTrophies")
    @Expose
    val totalTrophies: Int,

    @SerializedName("trophyLevel")
    @Expose
    val trophyLevel: Int,

    @SerializedName("worldRank")
    @Expose
    val worldRank: Int
) {
    fun toDomain(): Trophy {
        return Trophy(
            username = username,
            avatar = avatar,
            trophyLevel = trophyLevel,
            platinum = platinum,
            gold = gold,
            silver = silver,
            bronze = bronze,
            gamesPlayed = gamesPlayed,
            totalTrophies = totalTrophies,
            worldRank = worldRank
        )
    }
}