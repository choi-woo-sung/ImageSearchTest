package com.woosung.main

import com.woosung.core.base.BaseActivity
import com.woosung.imagesearch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(inflater = ActivityMainBinding::inflate)
