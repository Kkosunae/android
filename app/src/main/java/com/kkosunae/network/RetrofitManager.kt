package com.kkosunae.network

import android.util.Log
import com.kkosunae.model.FootData
import com.kkosunae.model.FootDataResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    private val iRetrofit : IRetrofit? = RetrofitClient.getClient()?.create(IRetrofit::class.java)

    fun postFootPrint(footData: FootData) {
        iRetrofit?.postFootPrint(footData)
            ?.enqueue(object :Callback<FootDataResponse> {
                override fun onResponse(
                    call: Call<FootDataResponse>,
                    response: Response<FootDataResponse>
                ) {

                    if (response.isSuccessful.not()) {
                        Log.d("RetrofitManager", "onResponse : " + response.message())
                    } else {
                        Log.d("RetrofitManager", "header : " + response.headers().toString())
                        Log.d("RetrofitManager", "body : " + response.body().toString())

                    }
                }

                override fun onFailure(call: Call<FootDataResponse>, t: Throwable) {
                    Log.d("RetrofitManager", "onFailure : $t")
                }
            })
    }
}