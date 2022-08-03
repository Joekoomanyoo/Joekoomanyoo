package com.ssafy.heritage.view.feed

import com.ssafy.heritage.adpter.FeedListAdapter
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentFeedListBinding

private const val TAG = "FeedListFragment___"

class FeedListFragment :
    BaseFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

//    private val feedAdapter: FeedListAdapter by lazy { FeedListAdapter() }

    override fun init() {

        initAdapter()
    }

    private fun initAdapter() = with(binding) {
//        recyclerview.adapter = feedAdapter

    }
}