package com.ssafy.heritage.view.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.KeywordListAdapter
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileBinding
import com.ssafy.heritage.listener.SettingListClickListener
import com.ssafy.heritage.util.ProfileSetting.LOGOUT
import com.ssafy.heritage.util.ProfileSetting.SIGNOUT
import com.ssafy.heritage.util.Setting.LICENSE
import com.ssafy.heritage.util.Setting.TERMS
import com.ssafy.heritage.util.Setting.VERSION_INFO
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.view.dialog.KeywordDialog
import com.ssafy.heritage.view.dialog.TermsDialog
import com.ssafy.heritage.view.login.LoginActivity
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


private const val TAG = "ProfileFragment___"

class ProfileFragment :
    BaseFragment<FragmentProfileBinding>(com.ssafy.heritage.R.layout.fragment_profile) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }
    private val keywordListAdapter: KeywordListAdapter by lazy { KeywordListAdapter() }
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val keywordDialog: KeywordDialog by lazy { KeywordDialog() }

    override fun init() {

        (requireActivity() as HomeActivity).setStatusbarColor("white")

        binding.user = userViewModel.user?.value!!

        initAdapter()

        initObserver()

        initClickListener()

//        setToolbar()

        setSwitch()
    }


    override fun onStart() {
        super.onStart()
        userViewModel.getKeywordList(userViewModel.user.value?.userSeq!!)
    }

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter

            addItemDecoration(
                com.ssafy.heritage.util.DividerItemDecoration(
                    5F,
                    resources.getColor(com.ssafy.heritage.R.color.athens_gray)
                )
            )

            settingListAdapter.settingListClickListener = object : SettingListClickListener {
                override fun onClick(position: Int, view: View) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
//                        MY_TRIP -> {
//                            findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_myDataFragment)
//                        }
//                        SCHEDULE -> {
//                            findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_myCalendarFragment)
//                        }
//                        SCRAP -> {
//                            findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_scrapListFragment)
//                        }
//                        MODIFY_PROFILE -> {
//                            findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_passwordRequestFragment)
//                        }
                        LOGOUT -> {
                            // 유저가 소셜로그인인 경우 구글 로그아웃도 해주셈
                            ApplicationClass.sharedPreferencesUtil.deleteToken()

//                            if (userViewModel.user.value?.socialLoginType == "social") {
//                                signOutGoogle()
//                            }

                            Intent(requireContext(), LoginActivity::class.java).apply {
                                startActivity(this)
                                requireActivity().finish()
                            }
                        }
                        SIGNOUT -> {
                            Withdrawal()
                        }
                        VERSION_INFO -> {
                            AwesomeDialog.build(requireActivity())
                                .title(VERSION_INFO)
                                .body(BuildConfig.VERSION_NAME)
                                .icon(R.drawable.ic_launcher_foreground)
                                .position(AwesomeDialog.POSITIONS.CENTER)
                                .onPositive(
                                    "닫기",
                                    buttonBackgroundColor = R.drawable.button_join,
                                    textColor = ContextCompat.getColor(
                                        requireContext(),
                                        android.R.color.black
                                    )
                                )
                        }
                        TERMS -> {
                            val termsDialog = TermsDialog()
                            termsDialog.show(childFragmentManager, "termsDialog")
                        }
                        LICENSE -> {
                            OssLicensesMenuActivity.setActivityTitle("오픈소스 라이센스 목록")
                            startActivity(
                                Intent(
                                    requireContext(),
                                    OssLicensesMenuActivity::class.java
                                )
                            )
                        }
                    }
                }
            }

            settingListAdapter.submitList(settingList)
        }

        // 키워드 리스트
//        recyclerviewKeyword.apply {
//            layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            adapter = keywordListAdapter
//
//            keywordListAdapter.kywordListLongClickListener = object : KeywordListLongClickListener {
//                override fun onClick(position: Int, scrapSeq: Int) {
//                    val yesOrNoDialog =
//                        YesOrNoDialog(title = "태그를 삭제할까요",
//                            positive = "삭제",
//                            negative = "취소",
//                            positiveClick = { userViewModel.deleteKeyword(scrapSeq) },
//                            negativeClick = { }
//                        )
//
//                    yesOrNoDialog.show(childFragmentManager, "yesOrNoDialog")
//                }
//            }
//        }

    }

    private fun initObserver() {

        userViewModel.user.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObserver asd: $it")
            binding.user = it
        }
//        userViewModel.keywordList.observe(viewLifecycleOwner) {
//            keywordListAdapter.submitList(it)
//        }

        userViewModel.notiSetting.observe(viewLifecycleOwner) {
            when (it) {
                'Y' -> binding.switchNoti.setChecked(true)
                'N' -> binding.switchNoti.setChecked(false)
            }
        }
        userViewModel.isNotiLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.switchNoti.isClickable = false
                false -> binding.switchNoti.isClickable = true
            }
        }
    }

    private fun initClickListener() = with(binding) {

        // 키워드 추가 클릭시
//        btnAddKeyword.setOnClickListener {
//            keywordDialog.show(childFragmentManager, "keywordDialog")
//        }

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnEdit.setOnClickListener {
            findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_passwordRequestFragment)
        }

        constraintMyScrap.setOnClickListener {
            findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_scrapListFragment)
        }

        constraintMyGroup.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("position", 1)
            }
            findNavController().navigate(R.id.action_profileFragment_to_groupListFragment, bundle)
        }

        constraintMyFeed.setOnClickListener {
            val bundle = Bundle().apply {
                putInt("position", 1)
            }
            findNavController().navigate(R.id.action_profileFragment_to_feedListFragment, bundle)
        }
    }

//    private fun setToolbar() = with(binding) {
//        toolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                // 설정 메뉴 클릭시
//                com.ssafy.heritage.R.id.setting -> {
//                    findNavController().navigate(com.ssafy.heritage.R.id.action_profileFragment_to_settingFragment)
//                    true
//                }
//                else -> {
//                    false
//                }
//            }
//        }
//    }

    private fun setSwitch() = with(binding) {
        // 알림
        switchNoti.setOnCheckedChangeListener {
            if (it) {
                // 알림 설정 'Y'로 서버에 보냄
                userViewModel.setNotiSetting('Y')
            } else {
                // 알림 설정 'N'으로 서버에 보냄
                userViewModel.setNotiSetting('N')
            }
        }
    }

    // 알림 설정, 로그아웃, 회원탈퇴, 버전 정보, 이용약관, 오픈소스 라이선스
    val settingList = arrayListOf<String>(VERSION_INFO, TERMS, LICENSE, LOGOUT, SIGNOUT)


    // 소셜 로그아웃
    private fun signOutGoogle() {

        mGoogleSignInClient.signOut()
            // 로그아웃 성공시
            .addOnSuccessListener {
                Log.d(TAG, "signOutGoogle: ")
            }
    }

    private fun Withdrawal() {

        CoroutineScope(Dispatchers.Main).launch {
            if (userViewModel.withdrawal() == true) {
                makeToast("회원 탈퇴가 완료되었습니다")
                ApplicationClass.sharedPreferencesUtil.deleteToken()
                Intent(requireContext(), LoginActivity::class.java).apply {
                    startActivity(this)
                }
                requireActivity().finish()
            } else {
                makeToast("탈퇴에 실패하였습니다")
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}