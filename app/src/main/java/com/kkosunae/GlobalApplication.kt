package com.kkosunae

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        NaverMapSdk.getInstance(this).client =
                NaverMapSdk.NaverCloudPlatformClient("m3lw8o5fjq")
        //kakao sdk init
        KakaoSdk.init(this, getString(R.string.kakao_app_key));
    }
}