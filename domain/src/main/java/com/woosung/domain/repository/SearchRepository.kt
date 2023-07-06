package com.woosung.domain.repository

import androidx.paging.PagingData
import com.woosung.domain.model.Document
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun fetchSearchImage(
        query: String,
    ): Flow<PagingData<Document>>
}
