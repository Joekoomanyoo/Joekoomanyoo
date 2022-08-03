package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupInfoBinding
import com.ssafy.heritage.view.HomeFragment
import com.ssafy.heritage.view.ar.ARFragment
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupInfoFragment___"

class GroupInfoFragment : BaseFragment<FragmentGroupInfoBinding>(R.layout.fragment_group_info) {

    private val args by navArgs<GroupInfoFragmentArgs>()
    private val groupViewModel by activityViewModels<GroupViewModel>()
    private lateinit var detailFragment : GroupDetailFragment
    private lateinit var chatFragment: GroupChatFragment
    private lateinit var calenderFragment: GroupCalenderFragment
    private lateinit var mapFragment: GroupMapFragment


    override fun init() {
        groupViewModel.getGroupList()
        groupViewModel.add(args.groupInfo)
        initAdapter()
        initObserver()
        initClickListener()
        Log.d(TAG, args.groupInfo.groupName)


        // groupViewModel.getGroupDetailInfo(args.groupInfo.groupSeq) //- 서버 API 수정중 : 서버 오류로 데이터 받아오는지 미확인
        // groupViewModel.selectGroupMembers(args.groupInfo.groupSeq) - 서버 API 미작성 : 회원 이미지,이름 나오는지 확인 필요
    }
    private fun initClickListener()= with(binding) {
        btnBack.setOnClickListener{
            findNavController().popBackStack()
        }
    }
    private fun initAdapter() {
        binding.apply {

            // 각 탭에 들어갈 프래그먼트 객체화
            detailFragment = GroupDetailFragment()
            chatFragment = GroupChatFragment()
            calenderFragment = GroupCalenderFragment()
            mapFragment = GroupMapFragment()

            childFragmentManager.beginTransaction()
                .replace(R.id.frame_layout_tab, detailFragment).commit()

            // 탭 클릭 시 이벤트
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab!!.position) {
                        0 -> replaceView(detailFragment)
                        1 -> replaceView(chatFragment)
                        2 -> replaceView(calenderFragment)
                        3 -> replaceView(mapFragment)
                    }
                }
                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    private fun initObserver() {
        groupViewModel.groupDetailInfo.observe(viewLifecycleOwner) {
            binding.apply {

            }
        }
    }

    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment?=null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.frame_layout_tab, it)?.commit()
        }
    }
}