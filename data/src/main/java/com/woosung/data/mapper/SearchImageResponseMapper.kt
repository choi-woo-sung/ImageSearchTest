package com.woosung.data.mapper

import com.woosung.data.model.remote.SearchImageResponse
import com.woosung.domain.model.SearchImage

internal object SearchImageResponseMapper : Mapper<SearchImageResponse, SearchImage> {
    override fun mapDomain(input: SearchImageResponse): SearchImage =
        SearchImage(
            info = MetaResponseMapper.mapDomain(input.meta),
            imageList = ImageResponseListMapper.mapDomainList(input.documents)
        )
}