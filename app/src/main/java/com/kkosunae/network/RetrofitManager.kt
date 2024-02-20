package com.kkosunae.network

import android.util.Log
import com.kkosunae.GlobalApplication
import com.kkosunae.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }
    private val TAG : String = "RetrofitManager"
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
    fun postGoogleLogin(googleRequest: GoogleRequest) : Boolean{
        var returnVar = true
        iRetrofit?.postGoogleLogin(googleRequest)
            ?.enqueue(object :Callback<GoogleResponse> {
                override fun onResponse(
                    call: Call<GoogleResponse>,
                    response: Response<GoogleResponse>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                        returnVar = false
                    } else {
                        Log.d(TAG, "header : " +response.headers().toString())
                        Log.d(TAG, "body : " + response.body().toString())
                        if (response.body()?.type.equals("register")) {
                            KakaoLoginApi.signUp(
                                SignUpInfo(
                                    response.body()?.socialLoginId.toString(),
                                    "name",
                                    "19970527",
                                    "male"
                                )
                            )
                        } else {
                            GlobalApplication.prefs.setString("jwt",response.body()?.jwt.toString())
                            //jwt 저장.
                        }
                        returnVar = true
                    }
                }

                override fun onFailure(call: Call<GoogleResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                    returnVar = false
                }

            })
        return returnVar
    }
}