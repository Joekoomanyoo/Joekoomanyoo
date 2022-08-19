package com.ssafy.heritage.view.group

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupMyListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.databinding.FragmentMyGroupListBinding
import com.ssafy.heritage.listener.EvaluationClickListener
import com.ssafy.heritage.listener.GroupMyListClickListener
import com.ssafy.heritage.viewmodel.GroupViewModel

private const val TAG = "MyGroupListFragment___"

class MyGroupListFragment :
    BaseFragment<FragmentMyGroupListBinding>(R.layout.fragment_my_group_list){

    private val groupViewModel by activityViewModels<GroupViewModel>()

    val lastGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }
    val applyGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }
    val joinGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }

    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    override fun init() {
        groupViewModel.selectMyGroups()
        initAdapter()
        initObserver()
    }

    private fun initAdapter() = with(binding) {
        recyclerviewLastGroup.apply {
            adapter = lastGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            lastGroupListAdapter.evaluationClickListener = object : EvaluationClickListener{
                override fun onClickEvaluationBtn(position: Int, myGroupResponse: MyGroupResponse) {
                    Log.d(TAG, "onClickEvaluationBtn:${myGroupResponse.groupSeq} ")

                    val action = GroupListFragmentDirections.actionGroupListFragmentToEvaluationListFragment(myGroupResponse)
                    findNavController().navigate(action)
                }
            }
            lastGroupListAdapter.groupMyListClickListener = object : GroupMyListClickListener {
                override fun onClick(position: Int, group: MyGroupResponse) {
                    val data = GroupListResponse(
                        group.groupSeq,
                        group.groupName,
                        group.groupImgUrl,
                        group.groupMaster,
                        group.groupDescription,
                        group.groupAccessType,
                        group.groupPassword,
                        group.groupMaxCount,
                        group.groupRegion,
                        group.groupStartDate,
                        group.groupEndDate,
                        group.groupAgeRange,
                        group.groupWithChild,
                        group.groupWithGlobal,
                        group.groupActive,
                        group.groupStatus
                    )
                    val action =
                        GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
                            data
                        )
                    findNavController().navigate(action)
                }
            }
        }
        recyclerviewApplyGroup.apply {
            adapter = applyGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            applyGroupListAdapter.groupMyListClickListener = object : GroupMyListClickListener {
                override fun onClick(position: Int, group: MyGroupResponse) {
                    val data = GroupListResponse(
                        group.groupSeq,
                        group.groupName,
                        group.groupImgUrl,
                        group.groupMaster,
                        group.groupDescription,
                        group.groupAccessType,
                        group.groupPassword,
                        group.groupMaxCount,
                        group.groupRegion,
                        group.groupStartDate,
                        group.groupEndDate,
                        group.groupAgeRange,
                        group.groupWithChild,
                        group.groupWithGlobal,
                        group.groupActive,
                        group.groupStatus
                    )
                    val action =
                        GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
                            data
                        )
                    findNavController().navigate(action)
                }
            }
        }
        recyclerviewJoinGroup.apply {
            adapter = joinGroupListAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            joinGroupListAdapter.groupMyListClickListener = object : GroupMyListClickListener {
                override fun onClick(position: Int, group: MyGroupResponse) {
                    val data = GroupListResponse(
                        group.groupSeq,
                        group.groupName,
                        group.groupImgUrl,
                        group.groupMaster,
                        group.groupDescription,
                        group.groupAccessType,
                        group.groupPassword,
                        group.groupMaxCount,
                        group.groupRegion,
                        group.groupStartDate,
                        group.groupEndDate,
                        group.groupAgeRange,
                        group.groupWithChild,
                        group.groupWithGlobal,
                        group.groupActive,
                        group.groupStatus
                    )
                    val action =
                        GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
                            data
                        )
                    findNavController().navigate(action)
                }
            }
        }
    }

    private fun initObserver() {
        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
            // 지난 모임
            val lastGroupList =
                it.filter { it.groupStatus == 'F' && it.memberStatus != 0 }
            // 신청한 모임
            val applyGroupList =
                it.filter { it.groupActive == 'Y' && it.groupStatus == 'R' && it.memberStatus == 0 }
            // 진행중 모임
            val joinGroupList =
                it.filter { it.groupActive == 'Y' && it.groupStatus != 'F' && it.memberStatus != 0 }

            Log.d(TAG, lastGroupList.size.toString())
            Log.d(TAG, applyGroupList.size.toString())
            Log.d(TAG, "joinGroupList:${joinGroupList}")

            lastGroupListAdapter.submitList(lastGroupList)
            applyGroupListAdapter.submitList(applyGroupList)
            joinGroupListAdapter.submitList(joinGroupList)
//            groupViewModel.selectMyGroups()
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

}