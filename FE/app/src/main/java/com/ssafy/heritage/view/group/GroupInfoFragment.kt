package com.ssafy.heritage.view.group

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.databinding.FragmentGroupInfoBinding
import com.ssafy.heritage.util.FileUtil
import com.ssafy.heritage.util.FormDataUtil
import com.ssafy.heritage.viewmodel.GroupViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody


private const val TAG = "GroupInfoFragment___"
private val PERMISSIONS_REQUIRED = arrayOf(
    Manifest.permission.READ_EXTERNAL_STORAGE
)

class GroupInfoFragment :
    BaseFragment<FragmentGroupInfoBinding>(com.ssafy.heritage.R.layout.fragment_group_info),
    TabLayout.OnTabSelectedListener {

    private val args by navArgs<GroupInfoFragmentArgs>()
    private val groupViewModel by activityViewModels<GroupViewModel>()

    //    private lateinit var detailFragment: GroupDetailFragment
//    private lateinit var chatFragment: GroupChatFragment
//    private lateinit var calenderFragment: GroupCalenderFragment
//    private lateinit var mapFragment: GroupMapFragment
    private lateinit var groupInfo: GroupListResponse

    var img_multipart: MultipartBody.Part? = null

    override fun init() {

        getGroupData()

    }

    private fun getGroupData() {
        CoroutineScope(Dispatchers.Main).launch {

            groupViewModel.add(args.groupInfo)
            val s = groupViewModel.selectGroupMembers(
                ApplicationClass.sharedPreferencesUtil.getUser(),
                args.groupInfo.groupSeq
            )

            binding.groupVM = groupViewModel

            groupViewModel.getChatList(args.groupInfo.groupSeq)
            groupViewModel.selectGroupDetail(args.groupInfo.groupSeq)
            groupViewModel.selectGroupSchedule(args.groupInfo.groupSeq)

            Log.d(TAG, "init CoroutineScope: $s")

            binding.groupDetailInfo = groupViewModel.detailInfo?.value!!

            initAdapter()

            initClickListener()
        }
        initObserver()
    }

    private fun initObserver() = with(binding) {
        groupViewModel.detailInfo.observe(viewLifecycleOwner) {
            groupDetailInfo = it
            groupInfo = it
        }
    }

    private fun initClickListener() = with(binding) {
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        // 설정
        binding.btnSetting.setOnClickListener {
            val action =
                GroupInfoFragmentDirections.actionGroupInfoFragmentToGroupModifyFragment(groupInfo)
            findNavController().navigate(action)
        }

        binding.btnChangeImage.setOnClickListener {
            if (!hasPermissions()) {
                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            } else {
                pick()
            }
        }
        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initAdapter() {
        binding.apply {
//
            // 각 탭에 들어갈 프래그먼트 객체화
//            detailFragment = GroupDetailFragment()
//            chatFragment = GroupChatFragment()
//            calenderFragment = GroupCalenderFragment()
//            mapFragment = GroupMapFragment()


            val adapter = FragmentPagerItemAdapter(
                childFragmentManager, FragmentPagerItems.with(requireContext())
                    .add("정보", GroupDetailFragment::class.java)
                    .add("채팅", GroupChatFragment()::class.java)
                    .add("일정", GroupCalenderFragment()::class.java)
                    .add("지도", GroupMapFragment()::class.java)
                    .create()
            )
            viewpager.offscreenPageLimit = 1
            viewpager.adapter = adapter
            viewpagertab.setViewPager(viewpager)

            val permission = groupViewModel.groupPermission?.value!!
//            if (permission == 3 || permission == 0) {
//                viewpager.setOnTouchListener { view, motionEvent ->
//                    true
//                }
//            }

            viewpagertab.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    if (position > 0) {
                        binding.motionlayout.transitionToEnd()
                    } else {
                        binding.motionlayout.transitionToStart()
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {
                }

            })

        }
    }


    private fun replaceView(tab: Fragment) {
        // 탭한 화면 변경
        var selectedFragment: Fragment? = null
        selectedFragment = tab
        selectedFragment?.let {
            childFragmentManager?.beginTransaction()?.replace(R.id.viewpager, it)?.commit()
        }
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
//        when (tab!!.position) {
//            0 -> replaceView(detailFragment)
//            1 -> replaceView(chatFragment)
//            2 -> replaceView(calenderFragment)
//            3 -> replaceView(mapFragment)
//        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}

    // 권한 있는지 체크
    fun hasPermissions() = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    // 사진 선택
    private fun pick() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        filterActivityLauncher.launch(intent)
    }

    // 사진 골라서 가져온 결과
    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data != null) {

                val imagePath = it.data!!.data
                Log.d(TAG, "imagePath: $imagePath")
                val file = FileUtil.toFile(requireContext(), imagePath!!)
                img_multipart = FormDataUtil.getImageBody("file", file)

                groupViewModel.updateGroupimage(img_multipart!!)

            } else if (it.resultCode == Activity.RESULT_CANCELED) {
                makeToast("사진 선택 취소")
            } else {
                Log.d("ActivityResult", "something wrong")
            }
        }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // PERMISSION GRANTED
            pick()
        } else {
            // PERMISSION NOT GRANTED
            makeToast("권한이 거부됨")
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}


