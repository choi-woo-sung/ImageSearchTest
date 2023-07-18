package com.woosung.domain.usecase

import com.woosung.domain.model.Document
import com.woosung.domain.repository.SearchRepository
import javax.inject.Inject

class GetBookMarkImageUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(): LinkedHashSet<Document> {
        return repository.getBookmarkList()
    }
}