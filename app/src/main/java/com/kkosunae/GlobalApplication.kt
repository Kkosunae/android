package com.kkosunae

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        //kakao sdk init
        KakaoSdk.init(this, "4a705167f1b89e0ab9347e08a2540f6f");
    }
}