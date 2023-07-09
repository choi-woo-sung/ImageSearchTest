package com.woosung.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.woosung.domain.model.Document
import com.woosung.domain.usecase.GetBookMarkImageUseCase
import com.woosung.domain.usecase.GetImageForPagingUseCase
import com.woosung.domain.usecase.ToggleBookMarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getImageForPagingUseCase: GetImageForPagingUseCase,
    private val toggleBookMarkUseCase: ToggleBookMarkUseCase,
    private val getBookMarkImageUseCase: GetBookMarkImageUseCase,
) : ViewModel() {
    private val _queryFlow = MutableSharedFlow<String>()

    private val _storeDocumentListFlow: MutableStateFlow<List<Document>> =
        MutableStateFlow(emptyList())
    val storeDocumentListFlow = _storeDocumentListFlow.asStateFlow()


    init {
        _storeDocumentListFlow.value = getBookMarkImageUseCase()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingFlow = _queryFlow.flatMapLatest {
        getImageForPagingUseCase(it)
    }.cachedIn(viewModelScope)


    fun handleQuery(query: String) {
        viewModelScope.launch {
            _queryFlow.emit(query)
        }
    }


    fun toggle(document: Document) = viewModelScope.launch {
        toggleBookMarkUseCase(document)
        val result = getBookMarkImageUseCase()
        _storeDocumentListFlow.update { result }
    }
}
