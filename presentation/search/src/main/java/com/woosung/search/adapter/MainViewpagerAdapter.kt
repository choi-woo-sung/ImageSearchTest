package com.woosung.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woosung.search.ui.ImageSearchFragment
import com.woosung.search.ui.ImageCollectionFragment


/**
 * Main ViewPager Adapter
 */
class MainViewpagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment = if (position == 0) {
        ImageSearchFragment()
    } else {
        ImageCollectionFragment()
    }

    override fun getItemCount(): Int = 2
}