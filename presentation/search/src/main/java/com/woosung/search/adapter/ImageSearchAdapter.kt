package com.woosung.search.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.woosung.domain.model.Document
import com.woosung.domain.model.Image
import com.woosung.domain.model.Video

/**
 * 이미지 검색 List Adapter
 *
 * @property toggleListener : toggle Event Listener
 */
class ImageSearchAdapter(private val toggleListener: (Document) -> Unit) :
    PagingDataAdapter<Document, ImageSearchViewHolder>(comparator) {

    private var collectionDocuments = mutableListOf<Document>() // 보관함에 저장된 Documents

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSearchViewHolder {
        return ImageSearchViewHolder.create(toggleListener, parent)
    }

    override fun onBindViewHolder(holder: ImageSearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item, collectionDocuments.contains(item))
        }
    }

    /**
     * 보관함에 저장된 Documents 표시 갱신 (보관함 저장 여부 확인용)
     *
     * @param documents : 보관함 Documents
     */
    fun setCollectionDocuments(documents: List<Document>) {
        this.collectionDocuments = documents.toMutableList()
    }

    companion object {
        val comparator = object : DiffUtil.ItemCallback<Document>() {
            override fun areItemsTheSame(oldItem: Document, newItem: Document): Boolean {
                return if (oldItem is Image && newItem is Image) oldItem.thumbnailUrl == newItem.thumbnailUrl
                else if (oldItem is Video && newItem is Video) oldItem.thumbnail == newItem.thumbnail
                else false
            }

            override fun areContentsTheSame(oldItem: Document, newItem: Document): Boolean {
                return if (oldItem is Image && newItem is Image) oldItem == newItem
                else if (oldItem is Video && newItem is Video) oldItem == newItem
                else false
            }
        }
    }
}