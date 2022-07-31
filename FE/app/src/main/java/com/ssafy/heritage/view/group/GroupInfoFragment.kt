package com.ssafy.heritage.view.group

import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupInfoBinding
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "GroupInfoFragment___"

class GroupInfoFragment : BaseFragment<FragmentGroupInfoBinding>(R.layout.fragment_group_info) {

    private val args by navArgs<GroupInfoFragmentArgs>()
    private val groupViewModel by activityViewModels<GroupViewModel>()

    override fun init() {
        initObserver()
        Log.d(TAG, args.groupInfo.groupName)
        // groupViewModel.getGroupDetailInfo(args.groupInfo.groupSeq) - 서버 API 수정중 : 서버 오류로 데이터 받아오는지 미확인
        // groupViewModel.selectGroupMembers(args.groupInfo.groupSeq) - 서버 API 미작성 : 회원 이미지,이름 나오는지 확인 필요
    }

    private fun initObserver() {
        groupViewModel.groupDetailInfo.observe(viewLifecycleOwner) {
            binding.apply {

            }
        }
    }
}