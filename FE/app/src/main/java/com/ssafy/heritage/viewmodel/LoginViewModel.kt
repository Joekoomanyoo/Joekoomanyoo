package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

private const val TAG = "LoginViewModel___"

class LoginViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    val id = MutableLiveData<String>()

    val pw = MutableLiveData<String>()

    // 일반로그인
    suspend fun login() = withContext(Dispatchers.Main) {

        val map = HashMap<String, String>()

        map.put("userId", id.value!!)
        map.put("userPassword", pw.value!!)

        // 서버에 로그인 요청
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.login(map)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "login response: $it")
            if (it.isSuccessful) {
                // 토큰값 반환
                it.body()
            } else {
                Log.d(TAG, "${it.code()}")
                makeToast("아이디, 비밀번호를 확인해주세요")
                null
            }
        }
    }

    // 아이디 중복검사 (소셜용)
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
                    // 중복이 아닐 경우 - 회원가입 시킴
                    "success" -> {
                        "signup"
                    }
                    // 소셜로그인 아이디로 이미 가입한 경우 -> 소셜 로그인 시킴
                    "fail social" -> {
                        socialLogin(id)
                    }
                    // 일반로그인 아이디로 이미 가입한 경우
                    else -> {
                        makeToast("이미 일반로그인으로 가입한 아이디 입니다")
                        "fail"
                    }
                }

            } else {
                Log.d(TAG, "socialCheckId response: ${it.errorBody()}")
                makeToast("탈퇴한 아이디로는 재가입이 불가능합니다")
                "fail"
            }
        }
    }

    // 소셜로그인 시킴
    suspend fun socialLogin(id: String) = withContext(Dispatchers.Main) {

        val map = HashMap<String, String>()

        map.put("userId", id)
        map.put("userPassword", "0")

        repository.socialLogin(map).let { response ->

            // 로그인 성공한 경우
            if (response.isSuccessful) {
                // 토큰값 반환
                response.body()
            } else {
                Log.d(TAG, "${response.code()}")
                makeToast("소셜 로그인 실패")
                "fail"
            }
        }
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }
}