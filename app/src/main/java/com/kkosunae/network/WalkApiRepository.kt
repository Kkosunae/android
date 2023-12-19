package com.kkosunae.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kkosunae.model.WalkEndData
import com.kkosunae.model.WalkStartData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WalkApiRepository {
    private const val TAG = "WalkStartRepository"
    private const val BASE_URL = "https://kkosunae.com/"
    private const val ACCESS_TOKEN = "123123"

    private var gson: Gson = GsonBuilder().setLenient().create()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    private val walkStartService: WalkApiService by lazy { retrofit.create(WalkApiService::class.java) }

    fun postWalkStart(walkStartData: WalkStartData) {
        walkStartService.postWalkStart(walkStartData)
            .enqueue(object :Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                    } else {
                        Log.d(TAG, response.headers().toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                }

            })
    }

    fun postWalkEnd(walkEndData: WalkEndData) {
        walkStartService.postWalkEnd(walkEndData)
            .enqueue(object :Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                    } else {
                        Log.d(TAG, response.headers().toString())
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                }

            })
    }
}