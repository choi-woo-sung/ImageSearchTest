package com.woosung.data.model.remote

import com.woosung.data.model.ResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class VideoResponse(
    @SerialName("title") val title: String,
    @SerialName("url") val url: String,
    @SerialName("datetime") val dateTime: String,
    @SerialName("play_time") val play_time: Int,
    @SerialName("thumbnail") val thumbnail: String,
    @SerialName("author") val author: String,
) : ResponseModel
