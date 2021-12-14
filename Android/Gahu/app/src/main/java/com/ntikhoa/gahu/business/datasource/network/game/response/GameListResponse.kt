package com.ntikhoa.gahu.business.datasource.network.game.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class GameListResponse(
    @SerializedName("page")
    @Expose
    val page: String,

    @SerializedName("games")
    @Expose
    val games: List<GameResponse>
) {
}