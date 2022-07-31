package com.ssafy.heritage.view.profile

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.FragmentPasswordRequestBinding

private const val TAG = "PasswordRequestFragment___"

class PasswordRequestFragment :
    BaseFragment<FragmentPasswordRequestBinding>(R.layout.fragment_password_request) {
    override fun init() {
        initClickListener()
    }

    private fun initClickListener() = with(binding) {
        btnOk.setOnClickListener {
            // 비밀번호가 입력되었는지 확인
            if (pw.isNullOrBlank()) {
                makeTextInputLayoutError(tilPw, "비밀번호를 입력해주세요")
                makeToast("비밀번호를 입력해주세요")
                return@setOnClickListener
            }

            // 서버에 비밀번호가 해당 유저의 비밀번호인지 확인 요청하고 성공하면 유저 정보 받아옴
            //// 비밀번호가 일치하면 받아온 유저정보를 가지고 ProfileModifyFragment로 이동
            val user = User(
                null,
                null,
                "id",
                "nickname",
                "",
                "2000",
                "normal",
                'M',
                "",
                "",
                "",
                "",
                "",
                'N'
            ) // 더미 데이터
            val action =
                PasswordRequestFragmentDirections.actionPasswordRequestFragmentToProfileModifyFragment(
                    user
                )
            findNavController().navigate(action)
        }
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }
}