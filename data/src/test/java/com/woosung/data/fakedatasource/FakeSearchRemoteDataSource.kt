package com.woosung.data.fakedatasource

import com.woosung.domain.datasource.SearchRemoteDataSource
import com.woosung.domain.model.DateTime
import com.woosung.domain.model.Image
import com.woosung.domain.model.Info
import com.woosung.domain.model.SearchImage
import com.woosung.domain.model.SearchVideo
import com.woosung.domain.model.Video
import java.text.SimpleDateFormat
import java.util.Date

class FakeSearchSuccessRemoteDataSourceImp : SearchRemoteDataSource {
    override suspend fun fetchSearchImage(query: String, page: Int): SearchImage = SearchImage(
        info = Info(
            totalCount = 9176,
            pageableCount = 1182,
            isEnd = false
        ), imageList = FakeData.fakeImage
    )

    override suspend fun fetchSearchVideo(query: String, page: Int): SearchVideo =
        SearchVideo(
            info = Info(
                totalCount = 7606,
                pageableCount = 9200,
                isEnd = false
            ), videoList = FakeData.fakeVideo
        )
}

object FakeData {
    val fakeVideo: List<Video> = listOf(
        Video(
            thumbnail = "테스트비디오1",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오2",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오3",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오4",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오5",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오6",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오7",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오8",
            dateTime = executeDateRandom()
        ),
        Video(
            thumbnail = "테스트비디오9",
            dateTime = executeDateRandom()
        )
    )

    val fakeImage: List<Image> = listOf(
        Image(
            thumbnailUrl = "테스트이미지1",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지2",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지3",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지4",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지5",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지6",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지7",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지8",
            dateTime = executeDateRandom()
        ),
        Image(
            thumbnailUrl = "테스트이미지9",
            dateTime = executeDateRandom()
        ),
    )
}

fun executeDateRandom(): DateTime {
    val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val currentTime = System.currentTimeMillis()
    val random = kotlin.random.Random(currentTime)

    // Randomly generate number of minutes to add/subtract (up to 1 day)
    val deltaMinutes = random.nextInt(1440) - 720

    val newDateTime = Date(currentTime + deltaMinutes * 60 * 1000)
    return DateTime(newDateTime)
}