package com.woosung.domain.usecase

import androidx.paging.PagingData
import com.woosung.domain.model.Document
import com.woosung.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleBookMarkUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(document: Document) {
        return repository.toggleBookmark(document)
    }
}