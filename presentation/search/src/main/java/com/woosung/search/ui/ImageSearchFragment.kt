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
        /**
         * 여기에 ceh를 안두는 이유는? 어차피 오류하나뜨면 다 전파되서 다 취소된다.
         */
        viewLifecycleOwner.lifecycleScope.launch {
            /**
             * 이거 꼭 써야하나?
             * Started?
             */
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    /**
                     *  반드시 collectLatest , 이전데이터를 무효화하기 떄문에
                     */

                    imageSearchViewModel.pagingFlow.collectLatest { items ->
                        // 검색 데이터 갱신
                        adapter.submitData(items)
                    }
                }
                /**
                 *  컬렉트 레이티스트의 장점을 그냥 말하기
                 */
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