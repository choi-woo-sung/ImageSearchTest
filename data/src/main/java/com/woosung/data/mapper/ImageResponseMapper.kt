package com.woosung.data.mapper

import com.woosung.data.model.remote.ImageResponse
import com.woosung.domain.model.DateTime
import com.woosung.domain.model.Image

internal object ImageResponseMapper : Mapper<ImageResponse, Image> {


    override fun mapDomain(input: ImageResponse): Image = with(input) {
        Image(
            thumbnailUrl = thumbnailUrl,
            width = width,
            height = height,
            dateTime = DateTime.fromTime(dateTime),
        )
    }
}
