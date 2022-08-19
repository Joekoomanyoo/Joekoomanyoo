package com.ssafy.heritage.view.setting

import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentNotiSettingBinding

private const val TAG = "NotiSettingFragment___"

class NotiSettingFragment :
    BaseFragment<FragmentNotiSettingBinding>(R.layout.fragment_noti_setting) {
    override fun init() {
        setSwitchListener()
    }

    private fun setSwitchListener() = with(binding) {

        // 모임 알림
        switchGroup.setOnCheckedChangeListener {
            if (it) {

            } else {

            }
        }

        //모임 가입 알림
        switchGroupApply.setOnCheckedChangeListener {
            if (it) {

            } else {

            }
        }

        // 채팅 알림
        switchChat.setOnCheckedChangeListener {
            if (it) {

            } else {

            }
        }

        // AR 알림
        switchAr.setOnCheckedChangeListener {
            if (it) {

            } else {

            }
        }
    }

}