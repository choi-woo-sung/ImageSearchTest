package com.woosung.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<BINDING : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup?, Boolean) -> BINDING
) : Fragment() {
    private var bindingRef: BINDING? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater(inflater, container, false).apply {
        bindingRef = this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
    }

    open fun initView() {}

    protected fun <T> binding(action: BINDING.() -> T): T {
        return checkNotNull(bindingRef) {
            "${this.javaClass.simpleName} - ViewDataBinding is released"
        }.action()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingRef = null
    }
}
