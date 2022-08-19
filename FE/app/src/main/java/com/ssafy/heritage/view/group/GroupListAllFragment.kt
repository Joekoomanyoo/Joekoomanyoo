package com.ssafy.heritage.view.group

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupListAllBinding
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.dialog.SelectCategoryDialog
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "GroupListAllFragment___"

class GroupListAllFragment :
    BaseFragment<FragmentGroupListAllBinding>(R.layout.fragment_group_list_all),
    OnItemClickListener {
    private lateinit var groupListAdapter: GroupListAdapter
    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    private val alphaInAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(groupListAdapter).apply {
            setDuration(300)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    override fun init() {

        groupViewModel.getGroupList()
        initAdapter()
        initObserver()
        initClickListener()
        setToolbar()
    }

    private fun initAdapter() {
        groupListAdapter = GroupListAdapter(this)
        binding.recyclerviewGroupList.adapter = alphaInAnimationAdapter
    }

    private fun initObserver() {
        groupViewModel.groupList.observe(viewLifecycleOwner) {
            // 진행
//            var list = mutableListOf<GroupListResponse>()
//            for (i in it) {
//                if (i.groupStatus == 'R') {
//                    list.add(i)
//                }
//            }

            val list = it.filter { it.groupActive == 'Y' && it.groupStatus == 'R' && it.groupMaster != userViewModel.user.value?.userNickname!!}
            groupListAdapter.submitList(list as MutableList<GroupListResponse>)
        }
    }

    private fun initClickListener() {
        binding.apply {
            fabCreateGroup.setOnClickListener {
                findNavController().navigate(R.id.action_groupListFragment_to_groupCreateFragment)
            }
//            btnMyGroup.setOnClickListener {
//                findNavController().navigate(R.id.action_groupListFragment_to_myGroupListFragment)
//            }
//            btnBack.setOnClickListener {
//                findNavController().popBackStack()
//            }
//            btnSort.setOnClickListener {
//                val selectCategoryDialog = SelectCategoryDialog()
//                selectCategoryDialog.show(childFragmentManager, "selectCategoryDialog")
//                selectCategoryDialog.adapter = groupListAdapter
//                selectCategoryDialog.list =
//                    groupViewModel.groupList.value!!.filter { it.groupStatus == 'R' } as MutableList<GroupListResponse>
//            }
        }
    }

    override fun onItemClick(position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
        val action = GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(
            groupListAdapter.getItem(position)
        )
        findNavController().navigate(action)
    }

    private fun setToolbar() = with(binding) {
        (parentFragment as GroupListFragment)._binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_sort -> {
                    val selectCategoryDialog = SelectCategoryDialog()
                    selectCategoryDialog.show(childFragmentManager, "selectCategoryDialog")
                    selectCategoryDialog.adapter = groupListAdapter
                    selectCategoryDialog.list =
                        groupViewModel.groupList.value!!.filter { it.groupStatus == 'R' } as MutableList<GroupListResponse>
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}