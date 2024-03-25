package com.kkosunae.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kkosunae.GlobalApplication
import com.kkosunae.model.*
import com.kkosunae.viewmodel.MainViewModel
import com.kkosunae.viewmodel.WalkStatisticViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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
    fun getWalkStatistics(viewModel : WalkStatisticViewModel) {
        retrofit().getWalkStatistics()
            .enqueue(object :Callback<WalkStatisticData> {
                override fun onResponse(call: Call<WalkStatisticData>, response: Response<WalkStatisticData>) {
                    if (response.isSuccessful.not()) {
                        Log.d(TAG, "onResponse : " + response.message())
                    } else {
                        Log.d(TAG, response.body().toString())
                        Log.d(TAG, "onResponse : " + response.message())
                        viewModel.setStatisticData(
                            WalkStatistic(response.body()!!.total,response.body()!!.recent,response.body()!!.weekly, response.body()!!.monthly)
                        )
                    }
                }

                override fun onFailure(call: Call<WalkStatisticData>, t: Throwable) {
                    Log.d(TAG, "onFailure : $t")

                }
            })
    }
    fun getWalkStatus(mainViewModel: MainViewModel) {
        retrofit().getWalkStatus()
            .enqueue(object :Callback<WalkStateData> {
                @RequiresApi(Build.VERSION_CODES.O)
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
                            var staringDate = response.body()?.startTime
                            Log.d(TAG, "@@@@@@@@@@@@@@@@@@@@startingData : $staringDate")
                            var startDate : LocalDateTime? = LocalDateTime.parse(staringDate?.substring(0,21))
                            val currentDateTime = LocalDateTime.now()

                            if (startDate != null) {
                                val dayBetween = ChronoUnit.DAYS.between(currentDateTime, startDate)
                                Log.d(TAG,"dayBetween : $dayBetween")
                                if (dayBetween > 0) {

                                }
                            }
//                            var date = response.body()?.startTime
//                            val hour = date?.substring(11,13)
//                            val minute = date?.substring(14,16)
//                            val second = date?.substring(17,19)
//
//                            Log.d(TAG, "starttime : $date")
//                            Log.d(TAG, "hour : $hour, minute : $minute, second : $second")
                            mainViewModel.setStartTime(staringDate)
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