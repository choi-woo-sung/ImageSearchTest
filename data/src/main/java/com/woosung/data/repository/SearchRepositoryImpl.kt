package com.woosung.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.woosung.data.source.SearchPagingSource
import com.woosung.domain.datasource.SearchRemoteDataSource
import com.woosung.domain.model.Document
import com.woosung.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class SearchRepositoryImpl @Inject constructor(
    private val searchDataSource: SearchRemoteDataSource,
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
                    searchDataSource,
                )
            },
        ).flow
    }
}
