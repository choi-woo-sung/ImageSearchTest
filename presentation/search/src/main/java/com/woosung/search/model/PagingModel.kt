package com.woosung.search.model

import com.woosung.domain.model.Document


sealed interface PagingModel {
    data class HeaderItem(val pageNum: Int) : PagingModel
    data class RepoItem(val document: Document, val pageNum: Int) : PagingModel
}