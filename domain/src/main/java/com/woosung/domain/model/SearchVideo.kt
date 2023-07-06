package com.woosung.domain.model

import com.woosung.domain.DomainModel

data class SearchVideo(
    val info: Info,
    val videoList: List<Video>,
) : DomainModel
