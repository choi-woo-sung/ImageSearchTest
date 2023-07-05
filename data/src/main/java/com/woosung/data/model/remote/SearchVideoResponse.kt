package com.woosung.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 검색 결과로 나온 비디오 데이터
 *
 * @property documents
 * @property meta
 */
@Serializable
internal data class SearchVideoResponse(
    @SerialName("documents") val documents: List<VideoResponse>,
    @SerialName("meta") val meta: MetaResponse,
)
