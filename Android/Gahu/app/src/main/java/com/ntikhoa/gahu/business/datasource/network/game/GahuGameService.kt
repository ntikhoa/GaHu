package com.ntikhoa.gahu.business.datasource.network.game

import com.ntikhoa.gahu.business.datasource.network.GenericResponse
import com.ntikhoa.gahu.business.datasource.network.game.response.PlatformResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface GahuGameService {

    @GET("platforms")
    suspend fun getPlatforms(
        @Header("Authorization") token: String
    ): GenericResponse<List<PlatformResponse>>
}