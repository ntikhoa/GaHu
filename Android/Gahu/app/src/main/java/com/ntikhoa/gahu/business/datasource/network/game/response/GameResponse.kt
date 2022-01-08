package com.ntikhoa.gahu.business.datasource.network.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.datasource.network.account.response.AccountResponse
import com.ntikhoa.gahu.business.domain.model.Game

data class GameResponse(
    @SerializedName("_id")
    @Expose
    val id: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("releaseDate")
    @Expose
    val releaseDate: String,

    @SerializedName("image")
    @Expose
    val imageUrl: String,

    @SerializedName("description")
    @Expose
    val description: String,

    @SerializedName("platforms")
    @Expose
    val platforms: List<PlatformResponse>,

    @SerializedName("author")
    @Expose
    val author: AccountResponse
) {
    fun toDomain(): Game {
        val platformsDomain = this.platforms.map { it.toDomain() }
        return Game(
            id = id,
            title = title,
            releaseDate = releaseDate,
            imageUrl = imageUrl,
            description = description,
            platforms = platformsDomain,
            author = author.toAuthorDomain()
        )
    }
}