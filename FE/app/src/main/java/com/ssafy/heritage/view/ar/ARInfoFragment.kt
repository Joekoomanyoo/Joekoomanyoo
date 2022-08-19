package com.ssafy.heritage.view.ar

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentARInfoBinding
import com.ssafy.heritage.viewmodel.ARViewModel

private const val TAG = "ARInfoFragment___"

class ARInfoFragment :
    BaseFragment<FragmentARInfoBinding>(com.ssafy.heritage.R.layout.fragment_a_r_info),
    TabLayout.OnTabSelectedListener {

    private val arViewModel by activityViewModels<ARViewModel>()

    private lateinit var arListFragment: ARListFragment
    private lateinit var arFoundFragment: ARFoundFragment

    var position = 0

    override fun init() {

        arguments?.let { position = it.get("position") as Int }


        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        binding.apply {

            // 각 탭에 들어갈 프래그먼트 객체화
            arListFragment = ARListFragment()
            arFoundFragment = ARFoundFragment()

            val adapter = FragmentPagerItemAdapter(
                childFragmentManager, FragmentPagerItems.with(requireContext())
                    .add("순위보기", ARListFragment()::class.java)
                    .add("도감보기", ARFoundFragment()::class.java)
                    .create()
            )

            viewpager.adapter = adapter
            viewpagertab.setViewPager(viewpager)

            viewpager.setCurrentItem(position, true)
        }
    }

    private fun initObserver() = with(binding) {
    }

    private fun initClickListener() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab!!.position) {
            0 -> replaceView(arListFragment)
            1 -> replaceView(arFoundFragment)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.viewpager, it)?.commit()
        }
    }
}