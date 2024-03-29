package com.kkosunae.model

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class WalkStartData(
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double
)
data class WalkStartResponse(
    @SerializedName("message") val message : String,
    @SerializedName("walkId") val walkId: Int,
)
data class WalkEndData(
    @SerializedName("walkId") val id : Int,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("distance") val distance : Int,
)
data class WalkStateData(
    @SerializedName("id") val id : Int,
    @SerializedName("startTime") val startTime : String,
)

data class WalkStatisticData(
    @SerializedName("total") val total : Total,
    @SerializedName("recent") val recent : Recent,
    @SerializedName("weekly") val weekly : Weekly,
    @SerializedName("monthly") val monthly : Monthly,
)