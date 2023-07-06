package com.woosung.domain.model

import com.woosung.domain.DomainModel

data class SearchImage(
    val info: Info,
    val imageList: List<Image>,
) : DomainModel


