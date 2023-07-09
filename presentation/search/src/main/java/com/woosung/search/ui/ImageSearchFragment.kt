package com.woosung.search.ui

import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.woosung.core.base.BaseFragment
import com.woosung.search.SearchViewModel
import com.woosung.search.adapter.ImageSearchAdapter
import com.woosung.search.databinding.FragmentImageSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * 이미지 검색 화면
 */
@AndroidEntryPoint
class ImageSearchFragment :
    BaseFragment<FragmentImageSearchBinding>(FragmentImageSearchBinding::inflate) {

    val imageSearchViewModel: SearchViewModel by activityViewModels()

    private val adapter: ImageSearchAdapter = ImageSearchAdapter {
        imageSearchViewModel.toggle(it)
    }

    override fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    imageSearchViewModel.pagingFlow.collectLatest { items ->
                        // 검색 데이터 갱신
                        adapter.submitData(items)
                    }
                }
                launch {

                    adapter.loadStateFlow.collectLatest {
                        // 검색 결과 이벤트 처리
                        when (val currentState = it.refresh) {
                            is LoadState.Loading -> {
                                binding {
                                    tvEmpty.visibility = View.GONE
                                }
                            }

                            is LoadState.Error -> {
                                Timber.d("error : ${currentState.error.message}")
                                Toast.makeText(
                                    requireContext(),
                                    currentState.error.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            is LoadState.NotLoading -> {
                                binding {
                                    if (it.append.endOfPaginationReached) {
                                        tvEmpty.visibility =
                                            if (adapter.itemCount == 0) View.VISIBLE else View.GONE
                                    }
                                }
                            }
                        }
                    }
                }
                launch {
                    imageSearchViewModel.storeDocumentListFlow.collect { documents ->
                        adapter.setCollectionDocuments(documents)
                    }
                }
            }
        }

        binding {
            recyclerView.adapter = adapter
            searchButton.setOnClickListener {
                searchImage()
            }
            swipeRefreshLayout.setOnRefreshListener {
                searchImage()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }


    private fun searchImage() {
        binding {
            if (searchEditText.text.isEmpty()) {
                Toast.makeText(requireContext(), "검색어를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                imageSearchViewModel.handleQuery(searchEditText.text.toString().trim())
            }
        }
    }
}