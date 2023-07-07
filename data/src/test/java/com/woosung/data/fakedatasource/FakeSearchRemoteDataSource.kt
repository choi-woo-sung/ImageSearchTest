package com.woosung.data.fakedatasource

import com.woosung.domain.datasource.SearchRemoteDataSource
import com.woosung.domain.model.DateTime
import com.woosung.domain.model.Image
import com.woosung.domain.model.Info
import com.woosung.domain.model.SearchImage
import com.woosung.domain.model.SearchVideo
import com.woosung.domain.model.Video
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import kotlin.random.Random

class FakeSearchSuccessRemoteDataSourceImp : SearchRemoteDataSource {
    override suspend fun fetchSearchImage(query: String, page: Int): SearchImage = SearchImage(
        info = Info(
            totalCount = 9176, pageableCount = 1182, isEnd = false
        ), imageList = FakeData.fakeImage[page - 1]
    )

    override suspend fun fetchSearchVideo(query: String, page: Int): SearchVideo = SearchVideo(
        info = Info(
            totalCount = 7606, pageableCount = 9200, isEnd = false
        ), videoList = FakeData.fakeVideo[page - 1]
    )
}

object FakeData {
    val fakeVideo: List<List<Video>> = listOf(
        listOf(
            Video(
                thumbnail = "테스트비디오1", dateTime = executeDateRandom()
            ), Video(
                thumbnail = "테스트비디오2", dateTime = executeDateRandom()
            ), Video(
                thumbnail = "테스트비디오3", dateTime = executeDateRandom()
            )
        ), listOf(
            Video(
                thumbnail = "테스트비디오4", dateTime = executeDateRandom()
            ), Video(
                thumbnail = "테스트비디오5", dateTime = executeDateRandom()
            ), Video(
                thumbnail = "테스트비디오6", dateTime = executeDateRandom()
            )
        ), listOf(
            Video(
                thumbnail = "테스트비디오7", dateTime = executeDateRandom()
            ), Video(
                thumbnail = "테스트비디오8", dateTime = executeDateRandom()
            ), Video(
                thumbnail = "테스트비디오9", dateTime = executeDateRandom()
            )
        )
    )

    val fakeImage: List<List<Image>> = listOf(
        listOf(
            Image(
                thumbnailUrl = "테스트이미지1", dateTime = executeDateRandom()
            ),
            Image(
                thumbnailUrl = "테스트이미지2", dateTime = executeDateRandom()
            ),
            Image(
                thumbnailUrl = "테스트이미지3", dateTime = executeDateRandom()
            ),
            Image(
                thumbnailUrl = "테스트이미지4", dateTime = executeDateRandom()
            ),
        ),
        listOf(
            Image(
                thumbnailUrl = "테스트이미지5", dateTime = executeDateRandom()
            ), Image(
                thumbnailUrl = "테스트이미지6", dateTime = executeDateRandom()
            ), Image(
                thumbnailUrl = "테스트이미지7", dateTime = executeDateRandom()
            ), Image(
                thumbnailUrl = "테스트이미지8", dateTime = executeDateRandom()
            ), Image(
                thumbnailUrl = "테스트이미지9", dateTime = executeDateRandom()
            )
        ),
        listOf()
    )
}

fun executeDateRandom(): DateTime {
    val start = Calendar.getInstance().apply {
        set(2021, 0, 1, 0, 0, 0)
    }.time.time
    val end = Calendar.getInstance().apply {
        set(2022, 0, 1, 0, 0, 0)
    }.time.time

    val randomTimeMillis = Random.nextLong(start, end)
    return DateTime(Date(randomTimeMillis))
}