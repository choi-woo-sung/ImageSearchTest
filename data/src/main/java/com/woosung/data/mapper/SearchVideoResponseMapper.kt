package com.woosung.data.mapper

import com.woosung.data.model.remote.SearchVideoResponse
import com.woosung.domain.model.SearchVideo

internal object SearchVideoResponseMapper : Mapper<SearchVideoResponse, SearchVideo> {
    override fun mapDomain(input: SearchVideoResponse): SearchVideo =
        SearchVideo(
            info = MetaResponseMapper.mapDomain(input.meta),
            videoList = VideoResponseListMapper.mapDomainList(input.documents)
        )
}