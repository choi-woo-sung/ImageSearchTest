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

            val page = with(pager) {
                refresh()
                append()
                append()
            } as PagingSource.LoadResult.Page

            val testList = mutableListOf<Document>().apply {
                addAll(FakeData.fakeImage)
                addAll(FakeData.fakeVideo)
            }

            Assertions.assertEquals(
                page.data,
                testList.sortedByDescending { it.dateTime.time() }
            )
        }
    }

}