package com.woosung.data.mapper

import com.woosung.data.model.remote.ImageResponse
import com.woosung.domain.model.Image

internal object ImageResponseListMapper : ListMapper<ImageResponse, Image> {
    override fun mapDomainList(input: List<ImageResponse>): List<Image> {
        return input.mapDomainList { ImageResponseMapper.mapDomain(it) }
    }

}
