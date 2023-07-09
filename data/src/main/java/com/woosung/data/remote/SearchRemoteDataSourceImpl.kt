package com.woosung.data.remote

import com.woosung.data.api.SearchAPI
import com.woosung.data.mapper.SearchImageResponseMapper
import com.woosung.data.mapper.SearchVideoResponseMapper
import com.woosung.domain.model.SearchImage
import com.woosung.domain.model.SearchVideo
import javax.inject.Inject

interface SearchRemoteDataSource {
    suspend fun fetchSearchImage(query: String, page: Int): SearchImage
    suspend fun fetchSearchVideo(query: String, page: Int): SearchVideo
}

internal class SearchRemoteDataSourceImpl @Inject constructor(
    private val searchAPI: SearchAPI,
) : SearchRemoteDataSource {


    override suspend fun fetchSearchImage(query: String, page: Int): SearchImage {
        val result = searchAPI.searchImagePerPage(query, page = page)
        return SearchImageResponseMapper.mapDomain(result)
    }

    override suspend fun fetchSearchVideo(query: String, page: Int): SearchVideo {
        val result = searchAPI.searchVideoPerPage(query, page = page)
        return SearchVideoResponseMapper.mapDomain(result)
    }
}
