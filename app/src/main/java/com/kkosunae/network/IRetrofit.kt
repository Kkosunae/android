package com.kkosunae.network

import com.google.gson.JsonElement
import com.kkosunae.model.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface IRetrofit {

    @GET("map/footprint")
    fun searchFootPrint() : Call<JsonElement>
    @POST("map/footprint")
    fun postFootPrint(@Body request: FootData) : Call<FootDataResponse>

    @POST("user/googleLogin")
    fun postGoogleLogin(@Body request: GoogleRequest): Call<GoogleResponse>
    @POST("user")
    fun postSingUp(@Body request: SignUpInfo): Call<KakaoResponse>
}
