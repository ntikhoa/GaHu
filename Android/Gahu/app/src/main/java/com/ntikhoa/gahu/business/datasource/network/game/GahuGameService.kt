package com.ntikhoa.gahu.business.datasource.network.game

import com.ntikhoa.gahu.business.datasource.network.GenericResponse
import com.ntikhoa.gahu.business.datasource.network.game.response.GameListResponse
import com.ntikhoa.gahu.business.datasource.network.game.response.GameResponse
import com.ntikhoa.gahu.business.datasource.network.game.response.PlatformResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GahuGameService {

    @GET("platforms")
    suspend fun getPlatforms(
        @Header("Authorization") token: String
    ): GenericResponse<List<PlatformResponse>>

    @GET("games")
    suspend fun getGames(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("platformId") platformId: String?
    ): GenericResponse<GameListResponse>
}