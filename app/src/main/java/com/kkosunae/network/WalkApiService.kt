package com.kkosunae.network

import com.kkosunae.model.WalkEndData
import com.kkosunae.model.WalkStartData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface WalkApiService {
    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNjk2ODM5NDM1LCJleHAiOjE3MDIwMjM0MzV9.yMd7qzcyqE34Mffi2O2SKSYOE_KMgufYJZPiK4yrm8E")
    @POST("walk/start")
    fun postWalkStart(@Body request: WalkStartData): Call<Void>

    @Headers("Authorization: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MiwiaWF0IjoxNjk2ODM5NDM1LCJleHAiOjE3MDIwMjM0MzV9.yMd7qzcyqE34Mffi2O2SKSYOE_KMgufYJZPiK4yrm8E")
    @POST("walk/end")
    fun postWalkEnd(@Body request: WalkEndData): Call<Void>

}