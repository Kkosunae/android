package com.kkosunae.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kkosunae.GlobalApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//싱글턴
object RetrofitClient {

    //레트로핏 클라이언트 선언
    private var retrofitClient: Retrofit? =null
//    private lateinit var retrofitClient : Retrofit
    private const val BASE_URL = "https://kkosunae.com/"

    private var gson:Gson = GsonBuilder().setLenient().create()

    fun okHttpClient(interceptor: AppInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
    }
    //레트로핏 클라이언트 가져오기
    fun getClient() :Retrofit? {
        if (retrofitClient == null) {
            //레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient(AppInterceptor()))
                .build()
        }
        return retrofitClient
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
}