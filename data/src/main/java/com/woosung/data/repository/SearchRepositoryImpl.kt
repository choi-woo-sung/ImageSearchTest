package com.woosung.data.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woosung.data.local.SearchSharedPreference
import com.woosung.data.remote.SearchRemoteDataSource
import com.woosung.data.source.SearchPagingSource
import com.woosung.domain.model.Document
import com.woosung.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: SearchRemoteDataSource,
    private val searchSharedPreference: SearchSharedPreference
) : SearchRepository {

    override suspend fun fetchSearchImage(query: String): Flow<PagingData<Document>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                SearchPagingSource(
                    query,
                    searchRemoteDataSource,
                )
            },
        ).flow
    }

    override fun getBookmarkList(): List<Document> {
        return searchSharedPreference.getDocuments()
    }

    override fun toggleBookmark(document: Document) {
        val documents = searchSharedPreference.getDocuments()
        // 기존 보관함 목록 포함여부 확인 후 추가 / 삭제 분기처리
        if (documents.contains(document)) {
            searchSharedPreference.removeDocument(document)
        } else {
            searchSharedPreference.addDocument(document)
        }

    }

}
