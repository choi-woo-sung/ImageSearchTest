package com.woosung.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<BINDING : ViewBinding>(
    private val inflater: (LayoutInflater) -> BINDING,
) : AppCompatActivity() {
    val binding: BINDING by lazy { inflater(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
    }

    /**
     * 뷰를 초기화하는 메서드
     *
     */
    open fun initView() {}

}
