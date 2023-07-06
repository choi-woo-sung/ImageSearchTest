package com.woosung.domain.datasource

import com.woosung.domain.model.SearchImage
import com.woosung.domain.model.SearchVideo

interface SearchRemoteDataSource {
    suspend fun fetchSearchImage(query: String, page: Int): SearchImage
    suspend fun fetchSearchVideo(query: String, page: Int): SearchVideo
}
