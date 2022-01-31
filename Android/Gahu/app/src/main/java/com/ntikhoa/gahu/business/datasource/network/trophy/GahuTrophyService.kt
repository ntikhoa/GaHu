package com.ntikhoa.gahu.business.datasource.network.trophy

import com.ntikhoa.gahu.business.datasource.network.GenericResponse
import com.ntikhoa.gahu.business.datasource.network.trophy.response.TrophyProfileResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface GahuTrophyService {

    @GET("trophies")
    suspend fun getTrophies(@Header("Authorization") token: String): GenericResponse<TrophyProfileResponse>
}