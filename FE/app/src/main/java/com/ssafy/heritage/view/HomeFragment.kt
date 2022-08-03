package com.ssafy.heritage.view

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupListAdapter
import com.ssafy.heritage.adpter.HeritageListAdapter
import com.ssafy.heritage.adpter.OnItemClickListener
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.Heritage
import com.ssafy.heritage.databinding.FragmentHomeBinding
import com.ssafy.heritage.listener.HeritageListClickListener
import com.ssafy.heritage.view.group.GroupListFragmentDirections
import com.ssafy.heritage.view.heritage.HeritageDetailFragment
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel

private const val TAG = "HomeFragment__"

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home),
    OnItemClickListener {

    private val groupListAdapter: GroupListAdapter by lazy { GroupListAdapter(this) }
    private val heritageAdapter: HeritageListAdapter by lazy { HeritageListAdapter() }

    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()


    override fun init() {

        initObserver()

        initAdapter()

        initClickListener()
    }

    private fun initObserver() {

        /*
        내가 가입한 모임 목록 groupListAdapter에 submitList 해줘야됨
         */

        heritageViewModel.heritageList.observe(viewLifecycleOwner) { list ->
            heritageAdapter.submitList(list.sortedBy { it.heritageScrapCnt })
        }

        userViewModel.user.observe(viewLifecycleOwner) { user ->
            Log.d(TAG, "initObserver: $user")
        }
    }

    private fun initAdapter() = with(binding) {

        // 나의 모임 리스트
        recyclerviewMyGroup.apply {
            adapter = groupListAdapter
        }

        // 추천 문화재 리스트
        recyclerviewHeritage.apply {
            adapter = heritageAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            heritageAdapter.heritageListClickListener = object : HeritageListClickListener {
                override fun onClick(position: Int, heritage: Heritage, view: View) {
                    // 해당 문화유산의 상세페이지로 이동
                    parentFragmentManager
                        .beginTransaction()
                        .addSharedElement(view, "heritage")
                        .addToBackStack(null)
                        .replace(
                            R.id.fragment_container_main,
                            HeritageDetailFragment.newInstance(heritage)
                        )
                        .commit()
                }
            }
        }

        // 추천 사진 리스트
        recyclerviewFeed.apply {
            /*
            어댑터 연결 필요
             */
        }
    }

    private fun initClickListener() = with(binding) {

        // 프로필 아이콘 클릭시
        ivMyprofile.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
        }

        // 알림 아이콘 클릭시
        ivNoti.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_notiFragment)
        }

        // 나의 모임 더보기 클릭시
        tvGoMyGroup.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_groupListFragment)
        }

        // 추천 문화재 더보기 클릭시
        tvGoHeritage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_heritageListFragment)
        }

        // 추천 사진 더보기 클릭시
        tvGoFeed.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_feedListFragment)
        }
    }

    // 나의 모임 아이템 클릭시
    override fun onItemClick(position: Int) {

        val action =
            GroupListFragmentDirections.actionGroupListFragmentToGroupInfoFragment(groupListAdapter.getItem(position))
        findNavController().navigate(action)
    }
}