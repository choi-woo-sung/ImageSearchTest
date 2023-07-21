package com.woosung.domain.repository

import androidx.paging.PagingData
import com.woosung.domain.model.Document
import com.woosung.domain.model.DocumentWithKey
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun fetchSearchImage(
        query: String,
    ): Flow<PagingData<DocumentWithKey>>

    /**
     * 기존에는 List였는데 LinkedHashSet으로 변경
     *
     * @return
     */
    fun getBookmarkList(): LinkedHashSet<Document>
     fun toggleBookmark(document: Document)
}
