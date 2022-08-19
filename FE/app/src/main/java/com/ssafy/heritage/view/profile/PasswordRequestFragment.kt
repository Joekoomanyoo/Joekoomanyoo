package com.ssafy.heritage.view.profile

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentPasswordRequestBinding
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "PasswordRequestFragment___"

class PasswordRequestFragment :
    BaseFragment<FragmentPasswordRequestBinding>(R.layout.fragment_password_request) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun init() {

        checkLoginType()

        initObserver()

        initClickListener()
    }

    private fun checkLoginType() {
        // 소셜로그인 유저는 바로 보내줌
        if (userViewModel.user.value?.socialLoginType == "social"){
            findNavController().navigate(R.id.action_passwordRequestFragment_to_profileModifyFragment)
        }
    }

    private fun initObserver() {
        userViewModel.user.observe(viewLifecycleOwner) {
            binding.user = it
        }
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
            CoroutineScope(Dispatchers.Main).launch {
                if (userViewModel.checkPassword(pw!!) == true) {
                    // ProfileModifyFragment로 이동
                    findNavController().navigate(R.id.action_passwordRequestFragment_to_profileModifyFragment)
                } else {
                    makeTextInputLayoutError(tilPw, "비밀번호가 일치하지 않습니다")
                    makeToast("비밀번호가 일치하지 않습니다")
                }
            }

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