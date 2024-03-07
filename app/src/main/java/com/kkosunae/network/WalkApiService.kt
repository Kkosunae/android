package com.kkosunae.network

import com.kkosunae.model.*

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST

interface WalkApiService {
    @POST("walk/start")
    fun postWalkStart(@Body request: WalkStartData): Call<WalkStartResponse>

    @POST("walk/end")
    fun postWalkEnd(@Body request: WalkEndData): Call<Void>

    @GET("walk/statistics")
    fun getWalkStatistics(): Call<Void>

    @GET("walk/status")
    fun getWalkStatus(): Call<WalkStateData>
}
interface LoginApiService {
    @POST("user/kakaoLogin")
    fun postKakaoLogin(@Body request: KakaoRequest): Call<KakaoResponse>
    @POST("user")
    fun postSingUp(@Body request: SignUpInfo): Call<KakaoResponse>
}