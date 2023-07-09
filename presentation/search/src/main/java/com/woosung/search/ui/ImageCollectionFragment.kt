package com.woosung.search.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.woosung.core.base.BaseFragment
import com.woosung.search.SearchViewModel
import com.woosung.search.databinding.FragmentImageSearchBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Image 보관함 화면
 */
class ImageCollectionFragment :
    BaseFragment<FragmentImageSearchBinding>(FragmentImageSearchBinding::inflate) {

    val imageSearchViewModel: SearchViewModel by viewModels()
    override fun initView() {
        binding {
            val adapter = ImageCollectionAdapter {
                imageSearchViewModel.toggle(it, requireContext())
            }
            recyclerView.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launch {
                imageSearchViewModel.dataFlow.collectLatest { documents ->
                    // 데이터 갱신
                    adapter.setItems(documents)
                }
            }
            // 저장 데이터 설정
            adapter.setItems(PrefRepository(requireContext()).getDocuments())
        }
    }
}