package com.woosung.search.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.woosung.domain.model.Document
import com.woosung.domain.model.Image
import com.woosung.domain.model.Video
import com.woosung.search.R
import com.woosung.search.databinding.LayoutListItemBinding

/**
 * 이미지 검색 ViewHolder
 *
 * @property toggleListener : toggle Event Listener
 * @property binding : ViewBinding
 *
 */
class ImageSearchViewHolder(
    private val toggleListener: (Document) -> Unit,
    private val binding: LayoutListItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    @SuppressLint("SetTextI18n")
    fun bind(document: Document, isCollect: Boolean) {
        // 객체별 이미지 url 구분
        val url = when (document) {
            is Image -> document.thumbnailUrl
            is Video -> document.thumbnail
        }

        Glide.with(binding.root).load(url).centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // 이미지 로드 성공 시 NO IMAGE 미표시
                    binding.tvNoImage.visibility = View.GONE
                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // 이미지 로드 실패 시 NO IMAGE 표시
                    binding.tvNoImage.visibility = View.VISIBLE
                    return false
                }
            }).into(binding.ivImage)

        // image toggle
        binding.ivImage.setOnClickListener {
            toggleListener.invoke(document)
        }

        // image 보관 여부 표시
        binding.ivSave.visibility = if (isCollect) View.VISIBLE else View.GONE

        // image 타입 표시
        val type = when (document) {
            is Image -> "<IMAGE>"
            is Video -> "<VIDEO>"
        }
        binding.tvDate.text = "${document.dateTime.toDateString()} $type"
    }

    companion object {
        fun create(collection: (Document) -> Unit, parent: ViewGroup): ImageSearchViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_list_item, parent, false)
            val binding = LayoutListItemBinding.bind(view)
            return ImageSearchViewHolder(collection, binding)
        }
    }
}