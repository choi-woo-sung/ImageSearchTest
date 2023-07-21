package com.woosung.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.woosung.domain.model.Document
import com.woosung.domain.usecase.GetBookMarkImageUseCase
import com.woosung.domain.usecase.GetImageForPagingUseCase
import com.woosung.domain.usecase.ToggleBookMarkUseCase
import com.woosung.search.model.PagingModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getImageForPagingUseCase: GetImageForPagingUseCase,
    private val toggleBookMarkUseCase: ToggleBookMarkUseCase,
    private val getBookMarkImageUseCase: GetBookMarkImageUseCase,
) : ViewModel() {
    private val _queryFlow = MutableSharedFlow<String>()

    private val _storeDocumentListFlow: MutableStateFlow<LinkedHashSet<Document>> =
        MutableStateFlow(linkedSetOf())

    val storeDocumentListFlow = _storeDocumentListFlow.asStateFlow()

    private val _errorHandler = CoroutineExceptionHandler { _, exception ->
        handleException(exception)
    }

    /**
     *
     */
    init {
        _storeDocumentListFlow.value = getBookMarkImageUseCase()
    }

    /**
     * Paging flow
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingFlow = _queryFlow.flatMapLatest {
        getImageForPagingUseCase(it)
    }.map { pagingData ->
        pagingData.map {
            PagingModel.RepoItem(it.document, it.key)
        }
    }.map { pagingModel ->
        pagingModel.insertSeparators { before, after ->
            if (after == null) {
                return@insertSeparators null
            }

            if (before == null) {
                return@insertSeparators PagingModel.HeaderItem(after.pageNum)
            }

            if (before.pageNum != after.pageNum) {
                return@insertSeparators PagingModel.HeaderItem(after.pageNum)
            } else {
                return@insertSeparators null
            }
        }
    }.cachedIn(viewModelScope)


    fun handleQuery(query: String) {
        viewModelScope.launch {
            _queryFlow.emit(query)
        }
    }


    /**
     * 듣기로는 말은 X, ~라고 알고 있습니다.
     *
     * @param document
     */
    fun toggle(document: Document) = viewModelScope.launch(_errorHandler) {
        toggleBookMarkUseCase(document)
        val result = getBookMarkImageUseCase()
        _storeDocumentListFlow.update { result }
    }

    fun update(){

    }

    /**
     * Coroutine Task 실행하며 감지되지 않은 예외케이스를 처리 합니다.
     *
     * @param 감지되지 않은 예외
     */
    fun handleException(exception: Throwable) {
        Timber.e(exception)
    }
}
