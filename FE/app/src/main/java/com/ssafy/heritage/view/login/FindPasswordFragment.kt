package com.ssafy.heritage.view.login

import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseFragment
import com.ssafy.heritage.databinding.FragmentFindPasswordBinding
import com.ssafy.heritage.view.HomeActivity
import com.ssafy.heritage.viewmodel.FindPasswordViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.regex.Pattern

private const val TAG = "FindPasswordFragment___"

class FindPasswordFragment :
    BaseFragment<FragmentFindPasswordBinding>(R.layout.fragment_find_password) {

    private val findPasswordViewModel by viewModels<FindPasswordViewModel>()

    override fun init() {

        (requireActivity() as LoginActivity).setStatusbarColor("main")

        binding.findVM = findPasswordViewModel

        initObserve()

        initClickListener()

        setTextChangedListener()

        // 이메일 인증 성공할 경우 비밀번호 창 띄움

        // 비밀번호 제대로 입력하면 수정ㄱㄱ
    }

    private fun initObserve() {
        findPasswordViewModel.message.observe(viewLifecycleOwner) {

            // viewModel에서 Toast메시지 Event 발생시
            it.getContentIfNotHandled()?.let {
                makeToast(it)
            }
        }
    }

    private fun initClickListener() = with(binding) {

        // 인증요청 클릭시
        btnRequestIdVertification.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {

                val pattern: Pattern = Patterns.EMAIL_ADDRESS

                // 이메일 형식이 맞을 경우
                if (!findPasswordViewModel.id.value.isNullOrBlank() && pattern.matcher(
                        findPasswordViewModel.id.value
                    ).matches()
                ) {

                    val result =
                        findPasswordViewModel.socialCheckId(findPasswordViewModel.id.value!!)
                    Log.d(TAG, "socialCheckId result: ${result}")

                    // id 중복이 아닌 경우
                    if (result == "success") {
                        makeToast("해당 아이디로 가입한 이력이 없습니다")
                        makeTextInputLayoutError(tilId, "해당 아이디로 가입한 이력이 없습니다")
                    }

                    // 소셜로그인 아이디인 경우
                    else if (result == "fail social") {
                        makeToast("소셜 로그인으로 가입한 아이디입니다")
                        makeTextInputLayoutError(tilId, "소셜 로그인으로 가입한 아이디입니다")
                    }

                    // 일반로그인인 경우
                    else {
                        tilIdVertification.visibility = View.VISIBLE
                        btnIdVertify.visibility = View.VISIBLE
                        findPasswordViewModel.sendIdVeroficationCode(tilIdVertification)
                    }
                } else {
                    makeTextInputLayoutError(tilId, "이메일 형식이 올바르지 않습니다")
                    makeToast("이메일 형식이 올바르지 않습니다")
                    false
                }
            }
        }

        // 인증하기 클릭시
        btnIdVertify.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {

                // 인증번호 인증 성공시
                if (findPasswordViewModel.idVerify(tilIdVertification)) {
                    tilId.isEnabled = false
                    btnRequestIdVertification.isEnabled = false
                    tilIdVertification.editText?.setText("인증성공")
                    tilIdVertification.isEnabled = false
                    btnIdVertify.isEnabled = false

                    headerPw.visibility = View.VISIBLE
                    headerPwCheck.visibility = View.VISIBLE
                    tilPw.visibility = View.VISIBLE
                    tilPwCheck.visibility = View.VISIBLE
                    btnPw.visibility = View.VISIBLE
                }
            }
        }

        // 비밀번호 변경 클릭시
        btnPw.setOnClickListener {
            // 비밀번호 유효성 검사 했는지 확인 (소셜 회원가입이 아닌 경우)
            if (!findPasswordViewModel.validatePw(tilPw, tilPwCheck)) {
                return@setOnClickListener
            }

            // 비밀번호 변경 요청
            CoroutineScope(Dispatchers.Main).launch {
                if (findPasswordViewModel.findPassword() == true) {
                    makeToast("비밀번호 변경이 완료되었습니다")
                    findNavController().popBackStack()
                } else {
                    makeToast("비밀번호 변경에 실패했습니다")
                }
            }
        }
    }

    private fun setTextChangedListener() = with(binding) {

        // id 이메일 입력창 에러 비활성화
        tilId.editText?.addTextChangedListener {
            tilId.isErrorEnabled = false
        }

        // 이메일 인증창 에러 비활성화
        tilIdVertification.editText?.addTextChangedListener {
            tilIdVertification.isErrorEnabled = false
        }

        // 비밀번호 입력창 에러 비활성화
        tilPw.editText?.addTextChangedListener {
            tilPw.isErrorEnabled = false
        }

        // 비밀번호 재확인 입력창 에러 비활성화
        tilPwCheck.editText?.addTextChangedListener {
            tilPwCheck.isErrorEnabled = false
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}