package com.ntikhoa.gahu.business.datasource.network.account

import com.ntikhoa.gahu.business.datasource.network.GenericResponse
import com.ntikhoa.gahu.business.datasource.network.account.response.AccountResponse
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header

interface GahuAccountService {

    @GET("user")
    suspend fun getUser(
        @Header("Authorization") token: String
    ): GenericResponse<AccountResponse>
}