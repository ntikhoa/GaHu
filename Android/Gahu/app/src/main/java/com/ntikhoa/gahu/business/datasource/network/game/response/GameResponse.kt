package com.ntikhoa.gahu.business.datasource.network.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ntikhoa.gahu.business.datasource.network.account.response.AccountResponse

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
}