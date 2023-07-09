package com.woosung.search.ui

import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.woosung.core.base.BaseFragment
import com.woosung.search.MainActivity
import com.woosung.search.SearchViewModel
import com.woosung.search.adapter.ImageSearchAdapter
import com.woosung.search.databinding.FragmentImageSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * 이미지 검색 화면
 */
@AndroidEntryPoint
class ImageSearchFragment :
    BaseFragment<FragmentImageSearchBinding>(FragmentImageSearchBinding::inflate) {

    val imageSearchViewModel: SearchViewModel by viewModels()

    private val adapter: ImageSearchAdapter = ImageSearchAdapter {
//        imageSearchViewModel.toggle(it, requireContext())
    }

    override fun initView() {
        viewLifecycleOwner.lifecycleScope.launch {
            imageSearchViewModel.pagingFlow.collectLatest { items ->
                // 검색 데이터 갱신
                adapter.submitData(items)
            }
        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            imageSearchViewModel.dataFlow.collectLatest { documents ->
//                // 보관함 데이터 갱신 (보관함 저장 여부 확인용)
//                adapter.setCollectionDocuments(documents)
//            }
//        }
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest {
                // 검색 결과 이벤트 처리
                when (val currentState = it.refresh) {
                    is LoadState.Loading -> {
                        binding {
                            tvEmpty.visibility = View.GONE
                        }
                    }

                    is LoadState.Error -> {
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

        // 저장 데이터 설정
//        adapter.setCollectionDocuments(PrefRepository(requireContext()).getDocuments())
        binding {

            recyclerView.adapter = adapter
            searchButton.setOnClickListener {
                // 검색 요청
                (activity as MainActivity).hideKeyboard()
                searchImage()
            }

            swipeRefreshLayout.setOnRefreshListener {
                // 당겨서 새로고침
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