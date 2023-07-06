package com.woosung.domain.model

import com.woosung.domain.DomainModel

data class Info(
    val totalCount: Int,
    val pageableCount: Int,
    val isEnd: Boolean,
) : DomainModel
