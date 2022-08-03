package com.ssafy.heritage.view.profile

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.KeywordListAdapter
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentProfileBinding
import com.ssafy.heritage.listener.SettingListClickListener
import com.ssafy.heritage.util.ProfileSetting.LOGOUT
import com.ssafy.heritage.util.ProfileSetting.MODIFY_PROFILE
import com.ssafy.heritage.util.ProfileSetting.MY_TRIP
import com.ssafy.heritage.util.ProfileSetting.SCHEDULE
import com.ssafy.heritage.util.ProfileSetting.SCRAP
import com.ssafy.heritage.util.ProfileSetting.SIGNOUT
import com.ssafy.heritage.view.login.LoginActivity
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ProfileFragment___"

class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {

    private val userViewModel by activityViewModels<UserViewModel>()
    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }
    private val keywordListAdapter: KeywordListAdapter by lazy { KeywordListAdapter() }
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun init() {

        initAdapter()

        initObserver()

        initClickListener()
    }

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

            settingListAdapter.settingListClickListener = object : SettingListClickListener {
                override fun onClick(position: Int, view: View) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
                        MY_TRIP -> {

                        }
                        SCHEDULE -> {

                        }
                        SCRAP -> {

                        }
                        MODIFY_PROFILE -> {
                            findNavController().navigate(R.id.action_profileFragment_to_passwordRequestFragment)
                        }
                        LOGOUT -> {
                            // 유저가 소셜로그인인 경우 구글 로그아웃도 해주셈
                            ApplicationClass.sharedPreferencesUtil.deleteToken()
                            Intent(requireContext(), LoginActivity::class.java).apply {
                                startActivity(this)
                                requireActivity().finish()
                            }
                        }
                        SIGNOUT -> {
                            Withdrawal()
                        }
                    }
                }
            }

            settingListAdapter.submitList(settingList)
        }

        // 키워드 리스트
        recyclerviewKeyword.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = keywordListAdapter

            keywordListAdapter.submitList(keyowrd_list)
        }

    }

    private fun initObserver() {
        /*
        키워드 목록 받아와야함
         */
    }

    private fun initClickListener() = with(binding) {

        // 설정 아이콘 클릭시
        btnSetting.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }
    }

    val settingList = arrayListOf<String>(MY_TRIP, SCHEDULE, SCRAP, MODIFY_PROFILE, LOGOUT, SIGNOUT)


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
            } else {
                makeToast("탈퇴에 실패하였습니다")
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }


    /*
    더미 데이터
     */
    val keyowrd_list = arrayListOf<String>("운전가능", "강한 뚜벅이", "수다쟁이", "수다쟁이", "문화초보")
}