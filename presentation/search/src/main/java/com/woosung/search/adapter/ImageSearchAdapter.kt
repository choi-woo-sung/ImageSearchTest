package com.woosung.search.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.woosung.domain.model.Document
import com.woosung.domain.model.Image
import com.woosung.domain.model.Video
import com.woosung.search.R
import com.woosung.search.model.PagingModel

/**
 * 이미지 검색 List Adapter
 *
 * @property toggleListener : toggle Event Listener
 */
class ImageSearchAdapter(private val toggleListener: (Document) -> Unit) :
    PagingDataAdapter<PagingModel, ViewHolder>(comparator) {

    private var collectionDocuments = linkedSetOf<Document>() // 보관함에 저장된 Documents
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val uiModel = getItem(position)
        uiModel?.let {
            when (uiModel) {
                is PagingModel.RepoItem -> {
                    (holder as ImageSearchViewHolder).bind(
                        uiModel.document,
                        // 최악의 경우 O(n)번째 까지 돈다.
                        // bind마다 호출하기때문에 꽤나 많이 호출될것같음.
                        // 여기서 비용을 줄인다면?
//                        collectionDocuments.contains(uiModel.document)
                    )
                }

                is PagingModel.HeaderItem -> {
                    (holder as ImageHeaderViewHolder).bind(uiModel.pageNum)
                }
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PagingModel.RepoItem -> R.layout.layout_list_item
            is PagingModel.HeaderItem -> R.layout.layout_list_header
            null -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == R.layout.layout_list_item) {
            ImageSearchViewHolder.create(toggleListener, parent)
        } else {
            ImageHeaderViewHolder.create(parent)
        }
    }


    /**
     * 보관함에 저장된 Documents 표시 갱신 (보관함 저장 여부 확인용)
     *
     * @param documents : 보관함 Documents
     */
    fun setCollectionDocuments(documents: LinkedHashSet<Document>) {
        this.refresh()
    }


    companion object {
        val comparator = object : DiffUtil.ItemCallback<PagingModel>() {
            override fun areItemsTheSame(oldItem: PagingModel, newItem: PagingModel): Boolean {
                return if (oldItem is PagingModel.RepoItem && newItem is PagingModel.RepoItem) {
                    //이미지는 썸네일URL이 같다면
                    if (oldItem.document is Image && newItem.document is Image)
                        oldItem.document.thumbnailUrl == newItem.document.thumbnailUrl
                    //비디오는 썸네일이 같다면
                    else if (oldItem.document is Video && newItem.document is Video)
                        oldItem.document.thumbnail == newItem.document.thumbnail
                    else false
                    //헤더는 페이지Num이 같다면
                } else if (oldItem is PagingModel.HeaderItem && newItem is PagingModel.HeaderItem) {
                    oldItem.pageNum == newItem.pageNum
                } else false
            }

            override fun areContentsTheSame(oldItem: PagingModel, newItem: PagingModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}