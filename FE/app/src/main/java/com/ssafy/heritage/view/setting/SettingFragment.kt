package com.ssafy.heritage.view.setting

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.awesomedialog.*
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.ssafy.heritage.R
import com.ssafy.heritage.adpter.SettingListAdapter
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentSettingBinding
import com.ssafy.heritage.listener.SettingListClickListener
import com.ssafy.heritage.util.Setting.LANGUAGE_SETTING
import com.ssafy.heritage.util.Setting.LICENSE
import com.ssafy.heritage.util.Setting.TERMS
import com.ssafy.heritage.util.Setting.VERSION_INFO
import com.ssafy.heritage.view.dialog.LanguageSettingDialog
import com.ssafy.heritage.view.dialog.TermsDialog
import com.ssafy.heritage.viewmodel.UserViewModel


private const val TAG = "SettingFragment___"

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    private val userViewModel by activityViewModels<UserViewModel>()

    private val settingListAdapter: SettingListAdapter by lazy { SettingListAdapter() }

    private val languageSettingDialog: LanguageSettingDialog by lazy { LanguageSettingDialog() }

    override fun init() {

        initAdapter()

        initObserver()

        setSwitch()
    }

    private fun initObserver() {
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

    private fun initAdapter() = with(binding) {

        // 설정 메뉴 리스트
        recyclerview.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = settingListAdapter

            addItemDecoration(
                com.ssafy.heritage.util.DividerItemDecoration(
                    5F,
                    resources.getColor(R.color.athens_gray)
                )
            )

            settingListAdapter.settingListClickListener = object : SettingListClickListener {
                override fun onClick(position: Int, view: View) {
                    val name = settingListAdapter.currentList[position]
                    when (name) {
//                        LANGUAGE_SETTING -> {
//                            languageSettingDialog.show(
//                                childFragmentManager,
//                                "languageSettingDialog"
//                            )
//                        }
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
//                            AwesomeDialog.build(requireActivity())
//                                .title(LICENSE)
//                                .body("라이센스 입니다")
//                                .icon(R.drawable.ic_launcher_foreground)
//                                .position(AwesomeDialog.POSITIONS.CENTER)
//                                .onPositive(
//                                    "닫기",
//                                    buttonBackgroundColor = R.drawable.button_join,
//                                    textColor = ContextCompat.getColor(
//                                        requireContext(),
//                                        android.R.color.black
//                                    )
//                                )
                        }
                    }
                }
            }

            settingListAdapter.submitList(settingList)
        }
    }


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

    val settingList =
        arrayListOf<String>(VERSION_INFO, TERMS, LICENSE)
}