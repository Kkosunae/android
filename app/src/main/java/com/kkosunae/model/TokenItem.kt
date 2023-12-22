package com.kkosunae.model

import com.google.gson.annotations.SerializedName

data class TokenItem (
    val accessToken : String,
    val refreshToken : String
)
data class KakaoRequest (
    @SerializedName("socialId") val type : String,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String

)
data class KakaoResponse (
    @SerializedName("type") val type : String,
    @SerializedName("jwt") val jwt : String,
    @SerializedName("socialLoginId") val socialLoginId : String

)

