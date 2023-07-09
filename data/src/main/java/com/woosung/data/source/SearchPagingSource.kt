package com.woosung.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woosung.data.remote.SearchRemoteDataSource
import com.woosung.domain.model.Document
import com.woosung.domain.model.DocumentWithKey

class SearchPagingSource(
    private val query: String,
    private val dataSource: SearchRemoteDataSource
) : PagingSource<Int, DocumentWithKey>() {

    override fun getRefreshKey(state: PagingState<Int, DocumentWithKey>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DocumentWithKey> {
        val page = params.key ?: defaultStart
        return try {
            val imageResponse = dataSource.fetchSearchImage(query, page = page)
            // VClip 검색
            val vClipResponse =
                dataSource.fetchSearchVideo(query = query, page = page)

            val nextPage =
                if (!imageResponse.info.isEnd || !vClipResponse.info.isEnd) page + 1 else null

            val data = mutableListOf<DocumentWithKey>()

            if (imageResponse.imageList.isNotEmpty()) data.addAll(imageResponse.imageList.map {
                DocumentWithKey(
                    page,
                    it
                )
            })
            if (vClipResponse.videoList.isNotEmpty()) data.addAll(vClipResponse.videoList.map {
                DocumentWithKey(
                    page,
                    it
                )
            })

            LoadResult.Page(
                // dateTime 내림차순 정렬
                data = data.sortedByDescending { it.document.dateTime.time() },
                nextKey = nextPage,
                prevKey = null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    companion object {
        const val defaultStart = 1 // 기본 시작값
    }
}