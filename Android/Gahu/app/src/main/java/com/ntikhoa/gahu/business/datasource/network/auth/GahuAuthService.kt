package com.ntikhoa.gahu.business.datasource.network.auth

import com.ntikhoa.gahu.business.datasource.network.GenericResponse
import com.ntikhoa.gahu.business.datasource.network.auth.response.LoginResponse
import retrofit2.http.Field
import retrofit2.http.POST

interface GahuAuthService {
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): GenericResponse<LoginResponse>
}