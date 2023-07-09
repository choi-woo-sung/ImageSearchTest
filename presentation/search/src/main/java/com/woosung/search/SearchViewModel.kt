package com.woosung.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.woosung.domain.usecase.GetImageForPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getImageForPagingUseCase: GetImageForPagingUseCase,
) : ViewModel() {
    private val _queryFlow = MutableStateFlow("")
    val queryFlow = _queryFlow.asStateFlow()
//
//    private val _searchedImages = MutableStateFlow<PagingData<Photo>>(PagingData.empty())
//    val searchedImages = _searchedImages

    @OptIn(ExperimentalCoroutinesApi::class)
    val pagingFlow = queryFlow.flatMapLatest {
        getImageForPagingUseCase(it)
    }.cachedIn(viewModelScope)


    fun handleQuery(query: String) {
        viewModelScope.launch {
            _queryFlow.emit(query)
        }
    }
}
