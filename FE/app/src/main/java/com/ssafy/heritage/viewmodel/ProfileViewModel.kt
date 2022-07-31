package com.ssafy.heritage.viewmodel

import android.util.Log
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

class ProfileViewModel : ViewModel() {

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val user = MutableLiveData<User>()

    val pw_check = MutableLiveData<String>()

    val oldNickname = MutableLiveData<String>()

    // 닉네임 중복확인 (클릭)
    suspend fun nicknameVerify(textInputLayout: TextInputLayout) = withContext(Dispatchers.Main) {

        // 기존 닉네임과 바꿀 닉네임이 같은 경우 그냥 통과시킴
        if (oldNickname.value == user.value!!.userNickname) {
            return@withContext true
        }

        // 서버에 닉네임 중복검사 요청 보냄
        repository.checkNickname(user.value!!.userNickname).let { response ->
            // 중복된 닉네임 없는 경우
            if (response.isSuccessful) {
                makeToast("사용 가능한 닉네임입니다")
                true
            } else {
                Log.d(TAG, "${response.code()}")
                makeTextInputLayoutError(textInputLayout, "중복된 닉네임입니다")
                makeToast("중복된 닉네임입니다")
                false
            }
        }
    }

    // 비밀번호 유효성 검사 실행
    fun validatePw(tilPw: TextInputLayout, tilPwCheck: TextInputLayout): Boolean {

        // 비밀번호를 입력안한 경우 통과
        if (user.value!!.userPassword.isNullOrBlank()) {
            return true
        }

        // 비밀번호 형식이 틀린 경우
        else {
            val pwRegex = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
            val pwPattern = Pattern.compile(pwRegex)
            if (!pwPattern.matcher(user.value!!.userPassword).matches()) {
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
        if (!user.value!!.userPassword.equals(pw_check.value)) {
            makeTextInputLayoutError(tilPwCheck, "비밀번호가 일치하지 않습니다")
            makeToast("비밀번호가 일치하지 않습니다")
            return false
        }

        // 유효성 검사를 다 통과한 경우
        return true
    }

    // 출생 년도 선택할 때마다 실행
    fun birthYearChanged(selected: String) {
        user.value!!.userBirth = selected
    }

    // 성별 선택할 때마다 실행
    fun genderTypeChanged(selected: String) {
        when (selected) {
            "남자" -> user.value!!.userGender = 'M'
            "여자" -> user.value!!.userGender = 'F'
        }
    }

    suspend fun modify() = withContext(Dispatchers.Main) {
        // 서버에 회원가입 요청
        val user = User(
            null,
            null,
            user.value!!.userId,
            user.value!!.userNickname,
            user.value!!.userPassword,
            user.value!!.userBirth,
            "normal",
            user.value!!.userGender,
            "",
            "",
            "",
            "",
            "",
            'N'
        )

        // 회원정보 수정하는 요청 보냄
//        repository.???(user).let { response ->
//            // 회원정보 수정 성공 시
//            if (response.isSuccessful) {
//                true
//            }
//            // 회원정보 수정 실패 시
//            else {
//                Log.d(TAG, "${response.code()}")
//                false
//            }
//        }
        true
    }

    // 프로필 사진 등록 요청 보냄
    suspend fun uploadPhoto() {

    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
    }
}