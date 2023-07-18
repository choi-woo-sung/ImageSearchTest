package com.woosung.core.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * presentation 별로 모듈을 나누려 했고, core를 가지려했으나, 페이지가 적어 하지않음
 *
 * @param BINDING
 * @property inflater
 */
abstract class BaseActivity<BINDING : ViewBinding>(
    private val inflater: (LayoutInflater) -> BINDING,
) : AppCompatActivity() {
    //LayoutInflater 넘김
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
