package com.woosung.data.model.remote

import com.woosung.data.model.DataModel
import com.woosung.data.model.ResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 검색 결과로 나온 이미지 데이터
 *
 * @property documents
 * @property meta
 */
@Serializable
internal data class SearchImageResponse(
    @SerialName("documents") val documents: List<ImageResponse>,
    @SerialName("meta") val meta: MetaResponse,
) : ResponseModel
