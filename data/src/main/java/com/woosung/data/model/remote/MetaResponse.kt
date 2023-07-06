package com.woosung.data.model.remote

import com.woosung.data.model.DataModel
import com.woosung.data.model.ResponseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 검색 할때 공통으로 들어가는 데이터
 *
 * @property totalCount 검색 결과로 나온 전체 개수
 * @property pageableCount 현재 노출 가능한 개수
 * @property isEnd 마지막 페이지면 true, 아니면 false
 */
@Serializable
internal data class MetaResponse(
    @SerialName("total_count") val totalCount: Int,
    @SerialName("pageable_count") val pageableCount: Int,
    @SerialName("is_end") val isEnd: Boolean,
) : ResponseModel
