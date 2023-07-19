package com.woosung.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.woosung.data.local.SearchSharedPreference
import com.woosung.data.remote.SearchRemoteDataSource
import com.woosung.domain.model.Document
import com.woosung.domain.model.DocumentWithKey


/**
 * refresh ->
 * scroll 이슈가 있었다.
 *
 * @property query
 * @property dataSource
 * @property sharedPreference
 * @constructor Create empty Search paging source
 */
class SearchPagingSource(
    private val query: String,
    private val dataSource: SearchRemoteDataSource,
    private val sharedPreference: SearchSharedPreference
) : PagingSource<Int, DocumentWithKey>() {

    override fun getRefreshKey(state: PagingState<Int, DocumentWithKey>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
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


            if (imageResponse.imageList.isNotEmpty()) {
                data.addAll(imageResponse.imageList.map {
                    DocumentWithKey(
                        page,
                        it
                    )
                })
            }
            if (vClipResponse.videoList.isNotEmpty()) {
                data.addAll(vClipResponse.videoList.map {
                    DocumentWithKey(
                        page,
                        it
                    )
                })
            }


            //contatins에 포함된 것만 수정한다.

            data.forEachIndexed { index, documentWithKey ->

                if (sharedPreference.getDocuments().contains(documentWithKey.document)) {
                    val prevValue = data[index]
                    data[index] = prevValue.copy(
                        isBookMarked = true
                    )
                }
            }




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