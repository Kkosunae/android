package com.kkosunae.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kkosunae.GlobalApplication
import com.kkosunae.model.*
import com.kkosunae.viewmodel.MainViewModel
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
            val jwtToken: String? = GlobalApplication.prefs.getString("jwt", "null")
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
    fun postWalkStart(walkStartData: WalkStartData, mainViewModel: MainViewModel) {
        retrofit().postWalkStart(walkStartData)
            .enqueue(object :Callback<WalkStartResponse> {
                override fun onResponse(
                    call: Call<WalkStartResponse>,
                    response: Response<WalkStartResponse>) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                    } else {
                        Log.d(TAG, "Success response")
                        Log.d(TAG, response.body().toString())
                        Log.d(TAG, response.body()?.message.toString())
                        Log.d(TAG, response.body()?.walkId.toString())

                        if(response.code() == 200) {
                            mainViewModel.upFootCount()
                            mainViewModel.setWalkId(response.body()?.walkId)
                        }
                    }
                }

                override fun onFailure(call: Call<WalkStartResponse>, t: Throwable) {
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
    fun getWalkStatistics() {
        retrofit().getWalkStatistics()
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
    fun getWalkStatus(mainViewModel: MainViewModel) {
        retrofit().getWalkStatus()
            .enqueue(object :Callback<WalkStateData> {
                override fun onResponse(call: Call<WalkStateData>, response: Response<WalkStateData>) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                        mainViewModel.setHomeMainBannerState(1)
                    } else {
                        Log.d(TAG, response.headers().toString())
                        if(response.code() == 200) {
                            Log.d(TAG, "response code : " + response.code())
                            // isWalking == true
                            mainViewModel.setHomeMainBannerState(0)
                            mainViewModel.setWalkId(response.body()?.id)
                        } else mainViewModel.setHomeMainBannerState(1)


                    }
                }

                override fun onFailure(call: Call<WalkStateData>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")
                    mainViewModel.setHomeMainBannerState(0)
                }
            })
    }
}