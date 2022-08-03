package com.ssafy.heritage.view.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentGroupListBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlin.properties.Delegates

private const val TAG = "GroupListFragment___"

class GroupListFragment :
    BaseFragment<FragmentGroupListBinding>(R.layout.fragment_group_list), OnItemClickListener {

    private lateinit var groupListAdapter: GroupListAdapter
    private val groupViewModel by viewModels<GroupViewModel>()
    private var groupSeq : Int = 0

    override fun init() {

        groupViewModel.getGroupList()
        initAdapter()
        initObserver()
        initClickListener()
    }

    private fun initAdapter() {
        groupListAdapter = GroupListAdapter(this)
        binding.recyclerviewGroupList.adapter = groupListAdapter
    }

    private fun initObserver() {
        groupViewModel.groupList.observe(viewLifecycleOwner) {
            groupListAdapter.submitList(it)
        }
    }

    private fun initClickListener() {
        binding.apply {
            fabCreateGroup.setOnClickListener {
                findNavController().navigate(R.id.action_groupListFragment_to_groupModifyFragment)
            }
        }
    }

    override fun onItemClick(position: Int) {
        val action = GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
            groupListAdapter.getItem(position)
        )
        findNavController().navigate(action)
    }

}