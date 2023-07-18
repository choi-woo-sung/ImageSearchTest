package com.woosung.search.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.woosung.domain.model.Document
import com.woosung.domain.model.Image
import com.woosung.domain.model.Video

/**
 * 이미지 보관함 List Adapter
 *
 * @property toggleListener : toggle Event Listener
 */
class ImageCollectionAdapter(private val toggleListener: (Document) -> Unit) :
    ListAdapter<Document, RecyclerView.ViewHolder>(comparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageSearchViewHolder {
        return ImageSearchViewHolder.create(toggleListener, parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ImageSearchViewHolder) {
            val document = getItem(position)
            holder.bind(document, true)
        }
    }


    fun setItems(items: List<Document>) {
        submitList(items)
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