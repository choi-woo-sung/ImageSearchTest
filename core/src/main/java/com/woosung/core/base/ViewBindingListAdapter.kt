package com.kts6056.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.woosung.core.base.ViewBindingViewHolder

abstract class ViewBindingListAdapter<BINDING : ViewBinding, ITEM>(
    diffCallback: DiffUtil.ItemCallback<ITEM>,
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING,
) : ListAdapter<ITEM, ViewBindingViewHolder<BINDING, ITEM>>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ) = ViewBindingViewHolder(
        inflater(LayoutInflater.from(parent.context), parent, false),
        onCreateViewHolder,
        bindItem,
    )

    override fun onBindViewHolder(
        holder: ViewBindingViewHolder<BINDING, ITEM>,
        position: Int,
    ) = holder.bindItem(getItem(position))

    /**
     * 최초 ViewHolder가 생성될때 실행되어야 할 코드를 작성합니다.
     * ex) View 에 대한 onClick 이벤트 처리
     */
    open val onCreateViewHolder: BINDING.(getItemPosition: () -> Int) -> Unit = {}

    /**
     * ViewHolder에 Item이 바인딩되어 동작되어야 할 코드를 작성합니다.
     */
    abstract val bindItem: BINDING.(item: ITEM, position: Int) -> Unit
}
