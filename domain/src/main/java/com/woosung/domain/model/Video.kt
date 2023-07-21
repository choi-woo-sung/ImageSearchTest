package com.woosung.domain.model

data class Video(
    val title: String = "",
    val thumbnail: String,
    override val dateTime: DateTime,
    override val isBookMarked: Boolean = false,
) : Document()
