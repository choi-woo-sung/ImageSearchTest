package com.woosung.data.mapper

import com.woosung.data.model.remote.VideoResponse
import com.woosung.domain.model.DateTime
import com.woosung.domain.model.Video

internal object VideoResponseMapper : Mapper<VideoResponse, Video> {
    override fun mapDomain(input: VideoResponse): Video = with(input) {
        Video(
            title = title,
            date = DateTime.fromTime(dateTime),
            thumbnail = thumbnail
        )
    }
}
