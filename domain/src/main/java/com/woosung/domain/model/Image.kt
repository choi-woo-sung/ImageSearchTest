package com.woosung.domain.model

/**
 *  Image의 도메인 모델, Video와 함께 쓰기 위해 Document를 상속받음
 *
 * @property thumbnailUrl
 * @property width
 * @property height
 * @property dateTime
 */
data class Image(
    val thumbnailUrl: String = "",
    val width: Int = 0,
    val height: Int = 0,
    override val dateTime: DateTime,
) : Document(dateTime)



