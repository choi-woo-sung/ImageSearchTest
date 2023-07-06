package com.woosung.data.mapper

import com.woosung.data.model.remote.MetaResponse
import com.woosung.data.model.remote.SearchImageResponse
import com.woosung.domain.model.Info
import com.woosung.domain.model.SearchImage

internal object MetaResponseMapper : Mapper<MetaResponse, Info> {
    override fun mapDomain(input: MetaResponse): Info = with(input) {
        Info(
            totalCount = totalCount,
            pageableCount = pageableCount,
            isEnd = isEnd
        )
    }
}