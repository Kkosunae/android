package com.kkosunae.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kkosunae.GlobalApplication
import com.kkosunae.model.KakaoRequest
import com.kkosunae.model.KakaoResponse
import com.kkosunae.model.SignUpInfo
import com.kkosunae.model.WalkEndData
import com.kkosunae.model.WalkStartData
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KakaoLoginApi {
    private const val TAG = "KakaoLoginApi"
    private const val BASE_URL = "https://kkosunae.com/"


    private var gson: Gson = GsonBuilder().setLenient().create()
    private fun okHttpClient(interceptor: AppInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    private fun retrofitLogin(): LoginApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient(AppInterceptor()))
            .build()
            .create(LoginApiService::class.java)
    }
    class AppInterceptor: Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val builder = chain.request().newBuilder()
            val jwtToken: String? = GlobalApplication.prefs.getString("accessToken", "null")
            if (jwtToken != null) {
                builder.addHeader("authorization", jwtToken)
            }
            return chain.proceed(builder.build())
        }
    }
    fun postKakaoLogin(kakaoRequest: KakaoRequest) {
        retrofitLogin().postKakaoLogin(kakaoRequest)
            .enqueue(object :Callback<KakaoResponse> {
                override fun onResponse(
                    call: Call<KakaoResponse>,
                    response: Response<KakaoResponse>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                    } else {
                        Log.d(TAG, "header : " +response.headers().toString())
                        Log.d(TAG, "body : " + response.body().toString())
                        if (response.body()?.type.equals("register")) {
                           signUp(SignUpInfo(response.body()?.socialLoginId.toString(), "name", "19970527", "male" ))
                        } else {
                            GlobalApplication.prefs.setString("jwt",response.body()?.jwt.toString())
                            //jwt 저장.
                        }

                    }
                }

                override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                }

            })
    }
    fun signUp(signUpInfo: SignUpInfo) {
        retrofitLogin().postSingUp(signUpInfo)
            .enqueue(object :Callback<KakaoResponse> {
                override fun onResponse(
                    call: Call<KakaoResponse>,
                    response: Response<KakaoResponse>
                ) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                    } else {
                        Log.d(TAG, "header : " +response.headers().toString())
                        Log.d(TAG, "body : " + response.body().toString())
                        GlobalApplication.prefs.setString("jwt",response.body()?.jwt.toString())
                    }
                }

                override fun onFailure(call: Call<KakaoResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")

                }

            })
    }
}