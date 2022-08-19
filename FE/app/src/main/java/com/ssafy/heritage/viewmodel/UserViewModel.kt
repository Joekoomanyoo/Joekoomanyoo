package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputLayout
import com.ssafy.heritage.data.dto.*
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.*
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response
import java.util.regex.Pattern

private const val TAG = "UserViewModel___"

class UserViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _scrapList = MutableLiveData<MutableList<Heritage>>()
    val scrapList: LiveData<MutableList<Heritage>>
        get() = _scrapList

    private val _keywordList = SingleLiveEvent<MutableList<Keyword>>()
    val keywordList: LiveData<MutableList<Keyword>>
        get() = _keywordList

    private val _myDestinationList = SingleLiveEvent<MutableList<GroupDestinationMap>>()
    val myDestinationList: LiveData<MutableList<GroupDestinationMap>>
        get() = _myDestinationList

    private val _myScheduleList = MutableLiveData<MutableList<Schedule>>()
    val myScheduleList: LiveData<MutableList<Schedule>>
        get() = _myScheduleList

    private val _isNotiLoading = MutableLiveData<Boolean>().apply { value = false }
    val isNotiLoading: LiveData<Boolean>
        get() = _isNotiLoading

    private val _notiSetting = MutableLiveData<Char>()
    val notiSetting: LiveData<Char>
        get() = _notiSetting

    private val _notiList = MutableLiveData<MutableList<Noti>>()
    val notiList: LiveData<MutableList<Noti>>
        get() = _notiList

    private val _myStampList = MutableLiveData<MutableList<Stamp>>()
    val myStampList: LiveData<MutableList<Stamp>>
        get() = _myStampList

    suspend fun setUser(user: User) = coroutineScope {
        getUserInfo(user.userSeq!!)
        Log.d(TAG, "setUser: ${_user.value}")
        getSchedule()
        getNotiList()
//        getMyStamp()
        true
    }

    // 회원 탈퇴
    suspend fun withdrawal() = withContext(Dispatchers.Main) {

        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.resign(user.value!!.userId)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "withdrawal response: $it")
            Log.d(TAG, "withdrawal response body: ${it.body()}")
            Log.d(TAG, "withdrawal response errorBody: ${it.errorBody()}")
            it.isSuccessful
        }
    }

    // 사용자 비밀번호가 맞는지 확인
    suspend fun checkPassword(pw: String) = withContext(Dispatchers.Main) {

        val map = HashMap<String, String>()

        map.put("userSeq", (_user.value!!.userSeq).toString())
        map.put("userPassword", pw)

        Log.d(TAG, "checkPassword map: $map")

        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.checkPassword(map)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "checkPassword response: $it")
            if (it.isSuccessful) {
                true
            } else {
                Log.d(TAG, "${it.code()}")
                null
            }
        }
    }

    // 닉네임 중복확인 (클릭)
    suspend fun nicknameVerify(
        newNickname: String,
        oldNickname: String,
        textInputLayout: TextInputLayout
    ) =
        withContext(Dispatchers.Main) {
            Log.d(TAG, "userNickname: ${user.value!!.userNickname}")
            Log.d(TAG, "newNickname: ${newNickname}")
            // 기존 닉네임과 바꿀 닉네임이 같은 경우 그냥 통과시킴
            if (user.value!!.userNickname.equals(newNickname)) {
                return@withContext true
            }

            // 서버에 닉네임 중복검사 요청 보냄
            var response: Response<String>? = null
            job = launch(Dispatchers.Main) {
                response = repository.checkNickname(newNickname)
            }
            job?.join()

            response?.let {
                Log.d(TAG, "nicknameVerify response: $it")
                if (it.isSuccessful) {
                    makeToast("사용 가능한 닉네임입니다")
                    true
                } else {
                    Log.d(TAG, "${it.code()}")
                    makeTextInputLayoutError(textInputLayout, "중복된 닉네임입니다")
                    makeToast("중복된 닉네임입니다")
                    false
                }
            }
        }

    // 비밀번호 유효성 검사 실행
    fun validatePw(
        pw: String?,
        pw_check: String?,
        tilPw: TextInputLayout,
        tilPwCheck: TextInputLayout
    ): Boolean {

        // 비밀번호를 입력안한 경우 통과
        if (pw.isNullOrBlank()) {
            return true
        }

        // 비밀번호 형식이 틀린 경우
        else {
            val pwRegex = """^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$"""
            val pwPattern = Pattern.compile(pwRegex)
            if (!pwPattern.matcher(pw).matches()) {
                makeTextInputLayoutError(tilPw, "비밀번호를 규칙을 만족하지 못합니다")
                makeToast("비밀번호를 규칙을 만족하지 못합니다")
                return false
            }
        }
        // 비밀번호 재확인 입력했는지 검사
        if (pw_check.isNullOrBlank()) {
            makeTextInputLayoutError(tilPwCheck, "비밀번호를 입력해주세요")
            makeToast("비밀번호를 입력해주세요")
            return false
        }
        // 두 비밀번호가 일치하지 않는 경우
        if (!pw.equals(pw_check)) {
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


    suspend fun modify(user: User, pw: String?) = withContext(Dispatchers.Main) {
        // 서버에 요청
        val userModify = UserModify(
            userSeq = user.userSeq!!,
            userNickname = user.userNickname,
            userPassword = pw,
            userBirth = user.userBirth,
            userGender = user.userGender,
            profileImgUrl = _user.value!!.profileImgUrl
        )
        Log.d(TAG, "modify: $userModify")
        // 회원정보 수정하는 요청 보냄
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.modifyProfile(userModify)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "modify response: ${it}")
            if (it.isSuccessful) {
                Log.d(TAG, "modify Successful: ${it.body()}")
                // 성공하면 회원정보 불러옴
                getUserInfo(user.userSeq!!)

                true
            } else {
                Log.d(TAG, "${it.code()}")
                false
            }
        }
    }

    // 내 정보 불러오기
    suspend fun getUserInfo(userSeq: Int) = withContext(Dispatchers.Main) {
        var response: Response<User>? = null

        job = launch(Dispatchers.Main) {
            response = repository.getUserInfo(userSeq)
        }
        job?.join()

        response?.let { it ->
            Log.d(TAG, "getUserInfo response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getUserInfo response: ${it.body()}")
                val result = it.body()
                val user = User(
                    userSeq = result!!.userSeq,
                    userId = result.userId,
                    userNickname = result.userNickname,
                    null,
                    userBirth = result.userBirth,
                    socialLoginType = result.socialLoginType,
                    userGender = result.userGender,
                    profileImgUrl = result.profileImgUrl
                )
                _user.value = user
                true
            } else {
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

    // 내 스크랩 목록 조회
    fun getScrapLIst() {
        // 비동기 작업할게
        viewModelScope.launch(Dispatchers.IO) {
            // 함수 호출 후 비동기적으로 응답 받음
            repository.selectAllScraps(user.value!!.userSeq!!).let { response ->
                if (response.isSuccessful) {
                    var list = response.body()!! as MutableList<Heritage>
                    _scrapList.postValue(list)
                    Log.d(TAG, "getScrapLIst: ${list}")
                } else {
                    Log.d(TAG, "${response.code()}")
                }
            }
        }
    }

    // 내 키워드 목록 조회
    fun getKeywordList(userSeq: Int) = viewModelScope.launch {
        var response: Response<List<Keyword>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllMyKeyword(userSeq)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getKeywordList response: $it")
            if (it.isSuccessful) {
                val list = it.body() as MutableList<Keyword>
                _keywordList.postValue(list)
            } else {

            }
        }
    }

    fun insertHeritageScrap(heritageScrap: HeritageScrap) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertHeritageScrap(heritageScrap).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "success scrap")
                    getScrapLIst()
                } else {
                    Log.d(TAG, "${response.code()}")
                }
            }
        }
    }

    fun deleteHeritageScrap(heritageSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteHeritageScrap(user.value!!.userSeq!!, heritageSeq).let {
                if (it.isSuccessful) {
                    Log.d(TAG, "삭제 성공?")
                    getScrapLIst()
                } else {
                    Log.d(TAG, "${it.code()}")
                }
            }
        }
    }

    // 내 키워드 추가
    fun insertKeyword(keyword: Keyword) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.insertMyKeyword(keyword)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "insertKeyword response: $it")
            if (it.isSuccessful) {
                getKeywordList(user.value?.userSeq!!)
            } else {

            }
        }
    }

    // 내 키워드 삭제
    fun deleteKeyword(myKeywordSeq: Int) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.deleteMyKeyword(myKeywordSeq)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "deleteKeyword response: $it")
            if (it.isSuccessful) {
                getKeywordList(user.value?.userSeq!!)
            } else {

            }
        }
    }

    // 내 목적지 불러오기
    fun getMydestination() = viewModelScope.launch {
        var response: Response<List<GroupDestinationMap>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllMyDestination()
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getMydestination response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getMydestination list: ${it.body()}")
                _myDestinationList.postValue(it.body() as MutableList<GroupDestinationMap>)
            } else {

            }
        }
    }

    // 내 일정 불러오기
    fun getSchedule() = viewModelScope.launch {
        var response: Response<List<Schedule>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllMySchedule(_user.value?.userSeq!!)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getSchedule response: $it")
            if (it.isSuccessful) {
                val list = it.body() as MutableList<Schedule>
                Log.d(TAG, "getSchedule list: $list")
                _myScheduleList.postValue(list)
            } else {

            }
        }
    }

    // 내 일정 추가하기
    fun addSchedule(schedule: Schedule) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.insertMySchedule(schedule)
        }
        job?.join()
        response?.let {
            Log.d(TAG, "addSchedule response: $it")
            if (it.isSuccessful) {
                getSchedule()
            } else {

            }
        }
    }

    // 내 일정 삭제하기
    fun deleteSchedule(scheduleSeq: Int) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.deleteMySchedule(scheduleSeq)
        }
        job?.join()
        response?.let {
            Log.d(TAG, "deleteSchedule response: $it")
            if (it.isSuccessful) {
                getSchedule()
            } else {

            }
        }
    }

    // 토큰 서버에 보내기
    fun pushToken(userSeq: Int, token: String) = viewModelScope.launch {

        val fcmToken = FCMToken(userSeq, token)
        repository.pushToken(fcmToken).let { response ->
            Log.d(TAG, "pushToken response: $response")
            if (response.isSuccessful) {
                Log.d(TAG, "getScrapLIst: 토큰 보내기 성공}")
            } else {
                Log.d(TAG, "${response.code()}")
            }
        }
    }

    // 알림 설정 정보 불러오기
    fun getNotiSetting() = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.getMyNotiSetting(_user.value?.userSeq!!)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getNotiSetting response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getNotiSetting Success: ${it.body()}")
                _notiSetting.postValue(it.body()?.toCharArray()!![0])
            } else {
                Log.d(TAG, "getNotiSetting error: ${it.errorBody()}")
            }
            _isNotiLoading.value = false
        }
    }

    // 알림 설정 하기
    fun setNotiSetting(flag: Char) = viewModelScope.launch {
        _notiSetting.value = flag
        _isNotiLoading.value = true

        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.setMyNotiSetting(_user.value?.userSeq!!, flag)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "setNotiSetting response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "setNotiSetting Success: ${it.body()}")
                getNotiSetting()
            } else {
                Log.d(TAG, "setNotiSetting error: ${it.errorBody()}")
            }
        }
    }

    // 내 알림 내역 리스트 불러오기
    fun getNotiList() = viewModelScope.launch {
        var response: Response<List<Noti>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllMyNoti(_user.value?.userSeq!!)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getNotiList response: $it")
            if (it.isSuccessful) {
                val list = it.body() as MutableList<Noti>
                Log.d(TAG, "getNotiList list: $list")
                _notiList.postValue(list)
            } else {

            }
        }
    }

    // 사진 전송하기
    suspend fun sendImage(img: MultipartBody.Part) = withContext(Dispatchers.Main) {
        Log.d(TAG, "sendImage: $img")
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.sendImage(img)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "sendImage response: $it")
            if (it.isSuccessful) {
                val body = JSONObject(it.body())
                val url = body.get("fileDownloadUri")
                Log.d(TAG, "sendImage body: ${url}")
                _user.value?.profileImgUrl = url as String
                true
            } else {
                false
            }
        }
    }

    // 내가 보유한 스탬프 불러오기
    fun getMyStamp() = viewModelScope.launch {
        var response: Response<List<Stamp>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.getMyStamp(_user.value?.userSeq!!)
        }
        job?.join()
        Log.d(TAG, "getMyStamp: StampStamp")
        response?.let {
            Log.d(TAG, "getMyStamp response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getMyStamp body: ${it.body()}")
                _myStampList.value = it.body() as MutableList<Stamp>?
            } else {
                Log.d(TAG, "getMyStamp: ${it.errorBody()}")
            }
        }
    }


}