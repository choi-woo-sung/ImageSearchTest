package com.woosung.data.remote

import com.woosung.data.model.remote.SearchImageResponse
import com.woosung.data.model.remote.SearchVideoResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface SearchAPI {

    @GET("image")
    suspend fun searchImagePerPage(
        @Query("query") query: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): SearchImageResponse

    @GET("vclip")
    suspend fun searchVideoPerPage(
        @Query("query") query: String,
        @Query("sort") sort: String = "recency",
        @Query("page") page: Int,
        @Query("size") size: Int = 10,
    ): SearchVideoResponse
}
