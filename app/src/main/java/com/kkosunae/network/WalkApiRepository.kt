package com.kkosunae.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kkosunae.GlobalApplication
import com.kkosunae.model.KakaoRequest
import com.kkosunae.model.KakaoResponse
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

object WalkApiRepository {
    private const val TAG = "WalkStartRepository"
    private const val BASE_URL = "https://kkosunae.com/"

    private var gson: Gson = GsonBuilder().setLenient().create()
    fun okHttpClient(interceptor: AppInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    fun retrofit(): WalkApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient(AppInterceptor()))
            .build()
            .create(WalkApiService::class.java)
    }
    fun retrofitLogin(): LoginApiService {
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
//    private val retrofit: Retrofit = Retrofit.Builder()
//        .baseUrl(BASE_URL)
//        .client(okHttpClient(AppInterceptor()))
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()

//    private val walkStartService: WalkApiService by lazy { retrofit.create(WalkApiService::class.java) }

//    class AppInterceptor : Interceptor {
//        @Throws(IOException::class)
//        override fun intercept(chain: Interceptor.Chain): okhttp3.Response = with(chain) {
//            val newRequest = request().newBuilder()
//                .addHeader("(header Key)", "(header Value)")
//                .build()
//            proceed(newRequest)
//        }
//
//    }


}
    fun postWalkStart(walkStartData: WalkStartData) {
        retrofit().postWalkStart(walkStartData)
            .enqueue(object :Callback<Void> {
                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>) {
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
        retrofit().postWalkEnd(walkEndData)
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