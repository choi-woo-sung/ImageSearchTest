package com.woosung.domain.model

data class Video(
    val title: String = "",
    val date: DateTime,
    val thumbnail: String,
) : Document(date)
