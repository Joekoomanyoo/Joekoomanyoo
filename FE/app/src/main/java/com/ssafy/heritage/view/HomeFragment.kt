package com.ssafy.heritage.view

import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.models.SlideModel
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.GroupMyListAdapter
import com.ssafy.heritage.adpter.HomeFeedAdapter
import com.ssafy.heritage.adpter.HomeGroupAdapter
import com.ssafy.heritage.adpter.HomeHeritageAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.databinding.FragmentHomeBinding
import com.ssafy.heritage.listener.FeedListClickListener
import com.ssafy.heritage.listener.HomeCategoryListClickListener
import com.ssafy.heritage.view.feed.FeedDetailFragment
import com.ssafy.heritage.viewmodel.FeedViewModel
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter

private const val TAG = "HomeFragment__"

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    private val myGroupListAdapter: GroupMyListAdapter by lazy { GroupMyListAdapter() }
    private val homeHeritageAdapter: HomeHeritageAdapter by lazy { HomeHeritageAdapter() }
    private val homeFeedAdapter: HomeFeedAdapter by lazy { HomeFeedAdapter() }
    private val homeGroupAdapter: HomeGroupAdapter by lazy { HomeGroupAdapter() }

    private val heritageViewModel by activityViewModels<HeritageViewModel>()
    private val userViewModel by activityViewModels<UserViewModel>()
    private val groupViewModel by activityViewModels<GroupViewModel>()
    private val feedViewModel by activityViewModels<FeedViewModel>()

    private val homeHeritageAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(homeHeritageAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    private val homeFeedAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(homeFeedAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    private val myGroupAnimationAdapter: ScaleInAnimationAdapter by lazy {
        ScaleInAnimationAdapter(myGroupListAdapter).apply {
            setDuration(2000)
            setInterpolator(OvershootInterpolator())
            setFirstOnly(false)
        }
    }

    val imageList = ArrayList<SlideModel>().apply {
        add(SlideModel(R.drawable.ad1))
        add(SlideModel(R.drawable.ad2))
        add(SlideModel(R.drawable.ad3))
        add(SlideModel(R.drawable.ad4))
        add(SlideModel(R.drawable.ad5))
        add(SlideModel(R.drawable.ad6))
        add(SlideModel(R.drawable.ad7))
        add(SlideModel(R.drawable.ad8))
        add(SlideModel(R.drawable.ad9))
        add(SlideModel(R.drawable.ad10))
    }


    override fun init() {

        (requireActivity() as HomeActivity).setStatusbarColor("main")

        initView()

        initObserver()

        initAdapter()

        initClickListener()

        setToolbar()
    }

    private fun initView() = with(binding) {
        imageSlide.setImageList(imageList)
    }

    private fun initObserver() {

//        heritageViewModel.heritageList.observe(viewLifecycleOwner) { list ->
//            // 스크랩 순으로 문화재 추천
//            homeHeritageAdapter.submitList(
//                list.sortedWith(
//                    compareBy({ -it.heritageScrapCnt },
//                        { it.heritageSeq })
//                )
//            )
//        }

        userViewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }

//        groupViewModel.myGroupList.observe(viewLifecycleOwner) {
//            myGroupListAdapter.submitList(it.filter { it.groupActive == 'Y' && it.groupStatus != 'F' && it.memberStatus != 0 })
//        }

        groupViewModel.groupList.observe(viewLifecycleOwner) {
            homeGroupAdapter.submitList(it)
        }

        feedViewModel.feedListAll.observe(viewLifecycleOwner) {
            homeFeedAdapter.submitList(it.sortedBy { it.createdTime }.reversed())
        }
    }

    private fun initAdapter() = with(binding) {

        // 나의 모임 리스트
//        recyclerviewMyGroup.apply {
//            adapter = myGroupAnimationAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//
//            myGroupListAdapter.groupMyListClickListener = object : GroupMyListClickListener {
//                override fun onClick(position: Int, group: MyGroupResponse) {
//                    val data = GroupListResponse(
//                        group.groupSeq,
//                        group.groupName,
//                        group.groupImgUrl,
//                        group.groupMaster,
//                        group.groupDescription,
//                        group.groupAccessType,
//                        group.groupPassword,
//                        group.groupMaxCount,
//                        group.groupRegion,
//                        group.groupStartDate,
//                        group.groupEndDate,
//                        group.groupAgeRange,
//                        group.groupWithChild,
//                        group.groupWithGlobal,
//                        group.groupActive,
//                        group.groupStatus
//                    )
//                    val action =
//                        HomeFragmentDirections.actionHomeFragmentToGroupInfoFragment(data)
//                    findNavController().navigate(action)
//                }
//            }
//        }

        // 문화재 카테고리
        recyclerviewHeritage.apply {
            adapter = homeHeritageAnimationAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 5)

            val list = arrayListOf("탑", "비", "불교", "공예품", "궁궐", "기록유산", "왕릉", "건축", "종", "기타")
            homeHeritageAdapter.submitList(list)

            homeHeritageAdapter.homeCategoryListClickListener =
                object : HomeCategoryListClickListener {
                    override fun onClick(position: Int) {
                        val bundle = Bundle().apply {
                            putInt("id", position + 1)
                        }
                        findNavController().navigate(
                            R.id.action_homeFragment_to_heritageListFragment,
                            bundle
                        )
                    }
                }

//            homeHeritageAdapter.heritageListClickListener = object : HeritageListClickListener {
//                override fun onClick(position: Int, heritage: Heritage, view: View) {
//
//                    heritageViewModel.setHeritage(heritage)
//
//                    // 해당 문화유산의 상세페이지로 이동
//                    parentFragmentManager
//                        .beginTransaction()
//                        .addSharedElement(view, "heritage")
//                        .addToBackStack(null)
//                        .replace(
//                            R.id.fragment_container_main,
//                            HeritageDetailFragment.newInstance(heritage)
//                        )
//                        .commit()
//                }
//            }
        }

        // 추천 사진 리스트
        recyclerviewFeed.apply {
            adapter = homeFeedAnimationAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

            homeFeedAdapter.feedListClickListener = object : FeedListClickListener {
                override fun onClick(position: Int, feed: FeedListResponse, view: View) {
                    parentFragmentManager
                        .beginTransaction()
                        .addSharedElement(view, "feed")
                        .addToBackStack(null)
                        .replace(
                            R.id.fragment_container_main,
                            FeedDetailFragment.newInstance(feed)
                        )
                        .commit()
                }
            }
        }

        // 마감임박 모임
//        recyclerviewGroup.apply {
//            adapter = homeGroupAdapter
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//
////            homeFeedAdapter.feedListClickListener = object : FeedListClickListener {
////                override fun onClick(position: Int, feed: FeedListResponse, view: View) {
////                    parentFragmentManager
////                        .beginTransaction()
////                        .addSharedElement(view, "feed")
////                        .addToBackStack(null)
////                        .replace(
////                            R.id.fragment_container_main,
////                            FeedDetailFragment.newInstance(feed)
////                        )
////                        .commit()
////                }
////            }
//        }
    }

    private fun initClickListener() = with(binding) {

        // 나의 모임 더보기 클릭시
//        tvGoMyGroup.setOnClickListener {
//            findNavController().navigate(R.id.action_homeFragment_to_groupListFragment)
//        }

        // 추천 문화재 더보기 클릭시
        tvGoHeritage.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_heritageListFragment)
        }

        // 추천 사진 더보기 클릭시
        tvGoFeed.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_feedListFragment)
        }

        // 모션레이아웃 설정
        motionlayout.addTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
                if (p0?.progress == 1.0F) {
                    btnArrow.tag = "down"
                    btnArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24)
                } else if (p0?.progress == 0.0F) {
                    btnArrow.tag = "up"
                    btnArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24)
                }
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}

        })

        // 코멘트 열고 닫기 버튼
        btnArrow.setOnClickListener {
            when (it.tag) {
                "down" -> motionlayout.transitionToStart()
                "up" -> motionlayout.transitionToEnd()
            }
        }
    }

    private fun setToolbar() = with(binding) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                // 프로필 메뉴 클릭시
                R.id.profile -> {
                    findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
                    true
                }
                // 알림 메뉴 클릭시
                R.id.notification -> {
                    findNavController().navigate(R.id.action_homeFragment_to_notiFragment)
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}