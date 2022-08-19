package com.ssafy.heritage.view.group

import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupListBinding
import com.ssafy.heritage.view.HomeActivity


private const val TAG = "GroupListFragment___"

class GroupListFragment :
    BaseFragment<FragmentGroupListBinding>(com.ssafy.heritage.R.layout.fragment_group_list) {

    var position = 0

    override fun init() {

        arguments?.let { position = it.get("position") as Int }

        (requireActivity() as HomeActivity).setStatusbarColor("main")

        initAdapter()

    }

    private fun initAdapter() = with(binding) {

        val adapter = FragmentPagerItemAdapter(
            childFragmentManager, FragmentPagerItems.with(requireContext())
                .add("전체 모임", GroupListAllFragment()::class.java)
                .add("나의 모임", MyGroupListFragment()::class.java)
                .create()
        )

        viewpager.adapter = adapter
        viewpagertab.setViewPager(viewpager)

//        viewpagertab.setOnTabClickListener {
//            binding.toolbar.menu.getItem(0).isVisible = it == 0
//        }

        viewpager.setCurrentItem(position, true)

        viewpagertab.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                binding.toolbar.menu.getItem(0).isVisible = position == 0
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })
    }
}