package com.kkosunae

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.kkosunae.utils.PreferenceUtil
import com.naver.maps.map.NaverMapSdk

class GlobalApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }
    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
        NaverMapSdk.getInstance(this).client =
                NaverMapSdk.NaverCloudPlatformClient("m3lw8o5fjq")
        var keyHash = Utility.getKeyHash(this)
        Log.i("GlobalApplication", "$keyHash")
        // kakao sdk init
        KakaoSdk.init(this, getString(R.string.kakao_app_key))
//        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)
    }
}