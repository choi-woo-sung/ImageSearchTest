package com.woosung.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woosung.search.R
import com.woosung.search.databinding.LayoutListHeaderBinding

class ImageHeaderViewHolder(
    private val binding: LayoutListHeaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: Int) {
        binding.tvIndex.text = text.toString()
    }

    companion object {
        fun create(parent: ViewGroup): ImageHeaderViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_header, parent, false)
            val binding = LayoutListHeaderBinding.bind(view)
            return ImageHeaderViewHolder(binding)
        }
    }
}