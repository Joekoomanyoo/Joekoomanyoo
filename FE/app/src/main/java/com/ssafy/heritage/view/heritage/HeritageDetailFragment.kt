package com.ssafy.heritage.view.heritage

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHeritageDetailBinding


private const val TAG = "HeritageDetailFragment___"

private const val ARG_HERITAGE = "heritage"

class HeritageDetailFragment :
    BaseFragment<FragmentHeritageDetailBinding>(R.layout.fragment_heritage_detail) {

    private var heritage: Heritage? = null
    private lateinit var heritageInfoFragment: HeritageInfoFragment
    private lateinit var heritageReviewFragment: HeritageReviewFragment

    @SuppressLint("LongLogTag")
    override fun init() {
        initAdapter()
        arguments?.let {
            heritage = it.getSerializable(ARG_HERITAGE) as Heritage
            Log.d(TAG, "init: $heritage")

            binding.heritage = heritage
        }

    }
    private fun initAdapter()=with(binding) {
        heritageInfoFragment = HeritageInfoFragment()
        heritageReviewFragment = HeritageReviewFragment()

        childFragmentManager.beginTransaction()
            // heritageInfoFragment를 먼저 띄우는거다
            .replace(R.id.fragment_container_view,heritageInfoFragment)
            .commit()

        // 탭 클릭 시 이벤트
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabSelected(tab: TabLayout.Tab?) {
                // 몇 번째 탭을 클릭했을 때
                when(tab!!.position) {
                    0 -> replaceView(heritageInfoFragment)
                    1 -> replaceView(heritageReviewFragment)
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
        })
    }
    companion object {
        fun newInstance(param: Heritage) =
            HeritageDetailFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_HERITAGE, param)
                }
            }
    }

    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment?=null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_view, it)?.commit()
        }
    }

}