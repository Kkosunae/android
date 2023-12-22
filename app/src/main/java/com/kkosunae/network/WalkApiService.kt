package com.kkosunae.network

import com.kkosunae.model.KakaoRequest
import com.kkosunae.model.KakaoResponse
import com.kkosunae.model.WalkEndData
import com.kkosunae.model.WalkStartData

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

import retrofit2.http.POST

interface WalkApiService {
    @POST("walk/start")
    fun postWalkStart(@Body request: WalkStartData): Call<Void>

    @POST("walk/end")
    fun postWalkEnd(@Body request: WalkEndData): Call<Void>
}
interface LoginApiService {
    @POST("user/kakaoLogin")
    fun postKakaoLogin(@Body request: KakaoRequest): Call<KakaoResponse>

}