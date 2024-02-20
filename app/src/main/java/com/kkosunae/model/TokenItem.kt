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
data class GoogleRequest (
    @SerializedName("socialId") val socialId : String,
    @SerializedName("name") val name : String,
    @SerializedName("email") val email : String
)
data class GoogleResponse (
    @SerializedName("type") val type : String,
    @SerializedName("jwt") val jwt : String,
    @SerializedName("socialLoginId") val socialLoginId : String
)
data class SignUpInfo (
    @SerializedName("socialLoginId") val socialLoginId : String,
    @SerializedName("name") val name : String,
    @SerializedName("birthday") val birthday : String,
    @SerializedName("gender") val gender : String,
)

