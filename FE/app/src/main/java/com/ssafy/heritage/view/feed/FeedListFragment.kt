package com.ssafy.heritage.view.feed

import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentFeedListBinding
import com.ssafy.heritage.view.HomeActivity

private const val TAG = "FeedListFragment___"

class FeedListFragment :
    BaseFragment<FragmentFeedListBinding>(R.layout.fragment_feed_list) {

    var position = 0

    override fun init() {

        arguments?.let {
            if (it.get("position") != null) {
                position = it.get("position") as Int
            }
        }

        (requireActivity() as HomeActivity).setStatusbarColor("main")

        initAdapter()

    }

//    private fun setChip() = with(binding) {
//        chipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
//            when (checkedIds[0]) {
//                R.id.tv_feed_my -> {
//                    selectedChip = 1
////                    val newList = dataList.filter { it.userSeq == userSeq }
////                    feedAdapter.submitList(newList)
//                    feedViewModel.getMyFeedList()
////                    strokeFeedAll.visibility = View.GONE
////                    strokeFeedMy.visibility = View.VISIBLE
////                    tvFeedAll.setTextColor(Color.parseColor("#8e8e93"))
////                    tvFeedMy.setTextColor(Color.parseColor("@color/main_color"))
//                    Log.d(TAG, "setChip: 나의 피드")
//                }
//                else -> {
////                    val newList = dataList.filter { true }
////                    feedAdapter.submitList(newList)
//                    feedViewModel.getFeedListAll()
////                    strokeFeedAll.visibility = View.VISIBLE
////                    strokeFeedMy.visibility = View.GONE
////                    tvFeedAll.setTextColor(Color.parseColor("@color/main_color"))
////                    tvFeedMy.setTextColor(Color.parseColor("#8e8e93"))
//                    Log.d(TAG, "setChip: 모두의 피드")
//                }
//            }
//        }
//    }

    private fun initAdapter() = with(binding) {

        val adapter = FragmentPagerItemAdapter(
            childFragmentManager, FragmentPagerItems.with(requireContext())
                .add("모두의 피드", FeedListAllFragment()::class.java)
                .add("나의 피드", FeedMyListFragment()::class.java)
                .create()
        )

        viewpager.adapter = adapter
        viewpagertab.setViewPager(viewpager)

        viewpager.setCurrentItem(position, true)
    }
}