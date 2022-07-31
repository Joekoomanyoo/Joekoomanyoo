package com.ssafy.heritage.viewmodel

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

private const val TAG = "JoinViewModel___"

class JoinViewModel : ViewModel() {

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val id = MutableLiveData<String>()

    val id_verification_code = MutableLiveData<String>()

    val nickname = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    val pw_check = MutableLiveData<String>()

    val birth = MutableLiveData<String>()

    val gender = MutableLiveData<Char>()

    val login_type = MutableLiveData<String>()

    val isCheckedNickname = MutableLiveData<Boolean>().apply { value = false }

    // id 이메일 인증번호 전송하기 (클릭)
    suspend fun sendIdVeroficationCode(textInputLayout: TextInputLayout) =
        withContext(Dispatchers.Main) {

            val pattern: Pattern = Patterns.EMAIL_ADDRESS
            // 이메일 형식이 맞을 경우
            if (!id.value.isNullOrBlank() && pattern.matcher(id.value).matches()) {

                // 이메일 중복 검사
                val res = checkId(id.value!!, textInputLayout)

                // 중복검사 통과시
                if (res) {
                    // 서버로 인증 요청 전송

                    ////// 요청 전송 성공시 true 리턴
                    true
                } else {
                    // 중복검사 실패시
                    makeToast("중복된 이메일입니다")
                    makeTextInputLayoutError(textInputLayout, "중복된 이메일입니다")
                    false
                }
            } else {
                makeTextInputLayoutError(textInputLayout, "이메일 형식이 올바르지 않습니다")
                makeToast("이메일 형식이 올바르지 않습니다")
                false
            }
        }

    // 아이디 중복검사
    suspend fun checkId(id: String, textInputLayout: TextInputLayout) =
        withContext(Dispatchers.Main) {
            // 서버에서 아이디 중복여부 요청

            repository.checkEmail(id).let { response ->
                // 중복된 아이디가 없는 경우
                if (response.isSuccessful) {
                    true
                } else {
                    Log.d(TAG, "${response.code()}")
                    makeTextInputLayoutError(textInputLayout, "중복된 이메일입니다")
                    makeToast("중복된 이메일입니다")
                    false
                }
            }
        }

    // id 이메일 인증하기 (클릭)
    fun idVerify(textInputLayout: TextInputLayout): Boolean {

        // 서버로 인증번호 보내고 맞게 입력했는지 검사

        //// 인증번호 통과했을 경우
        return true

        //// 실패했을 경우
        makeTextInputLayoutError(textInputLayout, "인증번호가 틀렸습니다")
//            return false
    }

    // 닉네임 중복확인 (클릭)
    suspend fun nicknameVerify(textInputLayout: TextInputLayout) = withContext(Dispatchers.Main) {

        // 서버에 닉네임 중복검사 요청 보냄

        repository.checkNickname(nickname.value!!).let { response ->
            // 중복된 닉네임 없는 경우
            if (response.isSuccessful) {
                isCheckedNickname.value = true
                makeToast("사용 가능한 닉네임입니다")
                true
            } else {
                Log.d(TAG, "${response.code()}")
                makeTextInputLayoutError(textInputLayout, "중복된 이메일입니다")
                makeToast("중복된 이메일입니다")
                false
            }
        }
    }

    // 비밀번호 유효성 검사 실행
    fun validatePw(tilPw: TextInputLayout, tilPwCheck: TextInputLayout): Boolean {

        // 비밀번호를 입력했는지 검사
        if (pw.value.isNullOrBlank()) {
            makeTextInputLayoutError(tilPw, "비밀번호를 입력해주세요")
            makeToast("비밀번호를 입력해주세요")
            return false
        }
        // 비밀번호 형식이 틀린 경우
        else {
            val pwRegex = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
            val pwPattern = Pattern.compile(pwRegex)
            if (!pwPattern.matcher(pw.value).matches()) {
                makeTextInputLayoutError(tilPw, "비밀번호를 규칙을 만족하지 못합니다")
                makeToast("비밀번호를 규칙을 만족하지 못합니다")
                return false
            }
        }
        // 비밀번호 재확인 입력했는지 검사
        if (pw_check.value.isNullOrBlank()) {
            makeTextInputLayoutError(tilPwCheck, "비밀번호를 입력해주세요")
            makeToast("비밀번호를 입력해주세요")
            return false
        }
        // 두 비밀번호가 일치하지 않는 경우
        if (!pw.value.equals(pw_check.value)) {
            makeTextInputLayoutError(tilPwCheck, "비밀번호가 일치하지 않습니다")
            makeToast("비밀번호가 일치하지 않습니다")
            return false
        }

        // 유효성 검사를 다 통과한 경우
        return true
    }

    // 출생 년도 선택할 때마다 실행
    fun birthYearChanged(selected: String) {
        birth.value = selected
    }

    // 성별 선택할 때마다 실행
    fun genderTypeChanged(selected: String) {
        when (selected) {
            "남자" -> gender.value = 'M'
            "여자" -> gender.value = 'F'
        }
    }

    suspend fun join() = withContext(Dispatchers.Main) {
        // 서버에 회원가입 요청
        val user = User(
            null,
            null,
            id.value!!,
            nickname.value!!,
            pw.value!!,
            birth.value!!,
            "normal",
            gender.value!!,
            "",
            "",
            "",
            "",
            "",
            'N'
        )
        repository.signup(user).let { response ->
            // 회원가입 성공 시
            if (response.isSuccessful) {
                true
            }
            // 회원가입 실패 시
            else {
                Log.d(TAG, "${response.code()}")
                false
            }
        }
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}