package com.woosung.search.ui

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.woosung.core.base.BaseFragment
import com.woosung.search.SearchViewModel
import com.woosung.search.adapter.ImageCollectionAdapter
import com.woosung.search.databinding.FragmentImageCollectionBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Image 보관함 화면
 */

@AndroidEntryPoint
class ImageCollectionFragment :
    BaseFragment<FragmentImageCollectionBinding>(FragmentImageCollectionBinding::inflate) {

    val imageSearchViewModel: SearchViewModel by activityViewModels()
    override fun initView() {
        binding {
            val adapter = ImageCollectionAdapter {
                imageSearchViewModel.toggle(it)
            }
            recyclerView.adapter = adapter

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    imageSearchViewModel.storeDocumentListFlow.collectLatest { documents ->
                        adapter.setItems(documents)
                    }
                }
            }
        }
    }
}