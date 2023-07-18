package com.woosung.data.pagingsource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
import com.woosung.data.fakedatasource.FakeData
import com.woosung.data.fakedatasource.FakeSearchSuccessRemoteDataSourceImp
import com.woosung.data.source.SearchPagingSource
import com.woosung.domain.model.Document
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested

class SearchPagingSourceTest {


    private lateinit var searchPagingSource: SearchPagingSource

    @Nested
    @DisplayName("페이징")
    inner class PagingSuccess {
        @BeforeEach
        fun initService() {
            searchPagingSource = SearchPagingSource("", FakeSearchSuccessRemoteDataSourceImp())
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        @org.junit.jupiter.api.Test
        @DisplayName("SearchPagingSource가 정상적으로 정렬하여 반환하는가?")
        fun verifySearchPagingSource() = runTest {

            val pager = TestPager(
                config = PagingConfig(pageSize = 3), pagingSource = searchPagingSource
            )

            with(pager) {
                refresh()
                append()
                append()
            }

            val testList = mutableListOf<Document>()

            for (i in 0 until FakeData.fakeImage.size) {
                val sortList = mutableListOf<Document>().run {
                    addAll(FakeData.fakeImage[i])
                    addAll(FakeData.fakeVideo[i])
                    sortedByDescending { it.dateTime.time() }
                }
                testList.addAll(sortList)
            }
            val result = pager.getPages().flatten().size

            Assertions.assertEquals(
                result,
                testList.size
            )

            Assertions.assertEquals(
                pager.getPages().flatten(),
                testList
            )
        }
    }

}