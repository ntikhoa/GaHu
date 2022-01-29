package com.ntikhoa.gahu.business.datasource.network.trophy

import com.ntikhoa.gahu.business.datasource.network.GenericResponse
import com.ntikhoa.gahu.business.datasource.network.trophy.response.TrophyResponse
import retrofit2.http.GET

interface GahuTrophyService {

    @GET("trophies")
    suspend fun getTrophies(token: String): GenericResponse<TrophyResponse>
}