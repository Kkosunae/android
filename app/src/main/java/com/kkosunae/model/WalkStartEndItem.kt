package com.kkosunae.model

import com.google.gson.annotations.SerializedName

data class WalkStartData(
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)
data class WalkEndData(
    @SerializedName("walkId") val id : Int,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)