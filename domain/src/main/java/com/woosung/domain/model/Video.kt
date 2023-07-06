package com.woosung.domain.model

data class Video(
    val title: String = "",
    override val dateTime: DateTime,
    val thumbnail: String,
) : Document(dateTime)
