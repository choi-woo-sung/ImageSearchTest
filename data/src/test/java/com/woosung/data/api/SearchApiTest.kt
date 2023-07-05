package com.woosung.data.api

import com.woosung.data.ApiAbstract
import com.woosung.data.remote.SearchAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class SearchApiTest : ApiAbstract<SearchAPI>() {
    private lateinit var service: SearchAPI

    @BeforeEach
    fun initService() {
        service = createService(SearchAPI::class.java)
    }

    @Nested
    @DisplayName("SearchAPI Image데이터가 200 떨어졌을때")
    inner class ImageDataSuccess {
        @BeforeEach
        fun setup() {
            enqueueResponse("/image/image_response_success.json")
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        @DisplayName("데이터 파싱과 응답코드가 정상적으로 반환")
        fun verifyGetImage() = runTest {
            val response = service.searchImagePerPage("test", page = 1)

            Assertions.assertEquals(
                response.documents[0].imageUrl,
                "http://t1.daumcdn.net/news/201706/21/kedtv/20170621155930292vyyx.jpg",
            )

            Assertions.assertEquals(
                response.documents[0].thumbnailUrl,
                "https://search2.kakaocdn.net/argon/130x130_85_c/36hQpoTrVZp",
            )
        }
    }

    @Nested
    @DisplayName("VideoAPI Video데이터가 200 떨어졌을때")
    inner class VideoDataSuccess {
        @BeforeEach
        fun setup() {
            enqueueResponse("/video/video_response_success.json")
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @Test
        @DisplayName("데이터 파싱과 응답코드가 정상적으로 반환")
        fun verifyGetImage() = runTest {
            val response = service.searchVideoPerPage("test", page = 1)

            Assertions.assertEquals(
                response.documents[0].thumbnail,
                "https://search2.kakaocdn.net/argon/138x78_80_pr/FRkbdWEKr4F",
            )

            Assertions.assertEquals(
                response.documents[0].url,
                "http://tv.kakao.com/channel/2653417/cliplink/304487728?playlistId=87634",
            )
        }
    }
}
