package com.woosung.search

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.google.android.material.tabs.TabLayoutMediator
import com.woosung.core.base.BaseActivity
import com.woosung.search.adapter.MainViewpagerAdapter
import com.woosung.search.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {
    override fun initView() {
        super.initView()
        val mainViewPager = binding.mainViewPager.apply {
            adapter = MainViewpagerAdapter(this@MainActivity)
        }
        // 탭에 viewpager2 를 연결하는 Mediator
        TabLayoutMediator(binding.mainTabLayout, mainViewPager) { tab, position ->
            tab.text = arrayOf("검색", "보관함")[position]
        }.attach()
    }


    /**
     * 키보드 숨김 처리
     */
    fun hideKeyboard() {
        val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}