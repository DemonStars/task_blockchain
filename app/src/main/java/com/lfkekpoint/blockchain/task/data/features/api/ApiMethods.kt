package com.lfkekpoint.blockchain.task.data.features.api

import com.lfkekpoint.blockchain.task.data.features.api.login.LoginApiResponse
import com.lfkekpoint.blockchain.task.data.features.api.refresh.TokenResponseData
import com.lfkekpoint.blockchain.task.domain.features.api.login.LoginReqData
import com.lfkekpoint.blockchain.task.domain.features.api.refresh.AuthRefreshReqData
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiMethods {

    @POST("/accounts/auth")
    fun login(@Body reqData: LoginReqData): Observable<LoginApiResponse>

    @POST("auth/refresh/")
    fun refreshToken(@Body reqData: AuthRefreshReqData): Call<TokenResponseData>
}