package com.ssafy.heritage.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.util.regex.Pattern

private const val TAG = "FindPasswordViewModel___"

class FindPasswordViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val id = MutableLiveData<String>()

    val id_verification_code = MutableLiveData<String>()

    val id_verification_code_check = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    val pw_check = MutableLiveData<String>()

    // id 이메일 인증번호 전송하기 (클릭)
    @SuppressLint("LongLogTag")
    suspend fun sendIdVeroficationCode(textInputLayout: TextInputLayout) =
        withContext(Dispatchers.Main) {
            // 서버로 인증 요청 전송
            var response: Response<String>? = null
            job = launch(Dispatchers.Main) {
                response = repository.emailAuth(id.value!!)
            }
            job?.join()

            response?.let {
                Log.d(TAG, "sendIdVeroficationCode response: $it")
                if (it.isSuccessful) {
                    // 요청 전송 성공시 인증번호 저장
                    id_verification_code_check.value = it.body()
                    Log.d(TAG, "sendIdVeroficationCode: ${it.body()}")
                    true
                } else {
                    Log.d(TAG, "${it.code()}")
                    makeToast("인증 요청 전송에 실패하였습니다")
                    false
                }
            }
        }

    // 아이디 중복검사 (소셜용)
    @SuppressLint("LongLogTag")
    suspend fun socialCheckId(id: String) = withContext(Dispatchers.Main) {
        // 서버에서 아이디 중복여부 요청
        Log.d(TAG, "socialCheckId: $id")
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.socialCheckId(id)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "socialCheckId response: $it")

            if (it.isSuccessful) {
                Log.d(TAG, "socialCheckId response: ${it.body()}")
                when (it.body()) {
                    // 중복이 아닐 경우
                    "success" -> {
                        "success"
                    }
                    // 소셜로그인 아이디로 이미 가입한 경우
                    "fail social" -> {
                        "fail social"
                    }
                    // 일반로그인 아이디로 이미 가입한 경우
                    else -> {
                        "fail"
                    }
                }

            } else {
                Log.d(TAG, "socialCheckId response: ${it.errorBody()}")
                makeToast("소셜로그인 오류")
                "fail"
            }
        }
    }

    // id 이메일 인증하기 (클릭)
    suspend fun idVerify(textInputLayout: TextInputLayout) = withContext(Dispatchers.Main) {

        // 인증번호 맞게 입력했는지 검사
        if (id_verification_code.value.equals(id_verification_code_check.value)) {
            true
        } else {
            makeTextInputLayoutError(textInputLayout, "인증번호가 틀렸습니다")
            false
        }
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }

    fun makeTextInputLayoutError(textInputLayout: TextInputLayout, msg: String) {
        textInputLayout.error = msg
        textInputLayout.isErrorEnabled = true
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

    // 비밀번호 재설정하기
    @SuppressLint("LongLogTag")
    suspend fun findPassword() = withContext(Dispatchers.Main) {
        val map = HashMap<String, String>()
        map.put("userId", id.value!!)
        map.put("userPassword", pw.value!!)

        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.findPassword(map)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "findPassword response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "findPassword Success: ${it.body()}")
                true
            } else {
                false
            }
        }
    }
}