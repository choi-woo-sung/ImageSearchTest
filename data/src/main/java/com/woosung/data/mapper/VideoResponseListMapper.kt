package com.woosung.data.mapper

import com.woosung.data.model.remote.VideoResponse
import com.woosung.domain.model.Video

internal object VideoResponseListMapper : ListMapper<VideoResponse, Video> {
    override fun mapDomainList(input: List<VideoResponse>): List<Video> {
        return input.mapDomainList { VideoResponseMapper.mapDomain(it) }
    }
}
