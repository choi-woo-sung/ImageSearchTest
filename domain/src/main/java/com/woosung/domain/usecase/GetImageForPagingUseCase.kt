package com.woosung.domain.usecase

import androidx.paging.PagingData
import com.woosung.domain.model.Document
import com.woosung.domain.model.DocumentWithKey
import com.woosung.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
class GetImageForPagingUseCase @Inject constructor(
    private val repository: SearchRepository,
) {
    suspend operator fun invoke(query: String): Flow<PagingData<DocumentWithKey>> {
        return repository.fetchSearchImage(query = query)
    }
}