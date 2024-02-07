package com.kkosunae.model

import android.media.Image
import com.google.gson.annotations.SerializedName
import java.net.URL

data class FootData(
    @SerializedName("content") val content : String,
    @SerializedName("latitude") val latitude : Double,
    @SerializedName("longitude") val longitude : Double,
    @SerializedName("image") val image : String ,
)
data class FootDataResponse(
    @SerializedName("message") val message : String,
    @SerializedName("footprint") val footprint : FootPrint,
    @SerializedName("error") val error : String,
)
data class FootPrint(
    val id : Int,
    val content : String,
    val latitude: Double,
    val longitude: Double,
    val user_id: Int,
    val isDeleted : Boolean,
    val updatedAt : String,
    val createdAt : String
)