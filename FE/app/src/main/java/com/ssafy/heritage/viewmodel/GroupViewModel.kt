package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.data.dto.Chat
import com.ssafy.heritage.data.dto.GroupDestinationMap
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.EvaluationRequest
import com.ssafy.heritage.data.remote.request.GroupAddRequest
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.request.GroupSchedule
import com.ssafy.heritage.data.remote.response.EvaluationProfileResponse
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response

private const val TAG = "GroupViewModel___"

class GroupViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    // viewModel에서 Toast 메시지 띄우기 위한 Event
    private val _message = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = _message

    private val _groupList = SingleLiveEvent<MutableList<GroupListResponse>>()
    val groupList: LiveData<MutableList<GroupListResponse>> get() = _groupList

    private val _groupMemberList = SingleLiveEvent<MutableList<Member>>()
    val groupMemberList: LiveData<MutableList<Member>> get() = _groupMemberList

    private val _groupPermission = SingleLiveEvent<Int>()
    val groupPermission: LiveData<Int> get() = _groupPermission

    private val _insertGroupInfo = SingleLiveEvent<GroupListResponse>()
    val insertGroupInfo: LiveData<GroupListResponse> get() = _insertGroupInfo

    private val _detailInfo = MutableLiveData<GroupListResponse>()
    val detailInfo: LiveData<GroupListResponse> get() = _detailInfo

    private val _approveState = SingleLiveEvent<Boolean>()
    val approveState: LiveData<Boolean> get() = _approveState

    private val _applyState = SingleLiveEvent<Boolean>()
    val applyState: LiveData<Boolean> get() = _applyState

    private val _myGroupList = MutableLiveData<MutableList<MyGroupResponse>>()
    val myGroupList: LiveData<MutableList<MyGroupResponse>> get() = _myGroupList

    private val _groupDestination = MutableLiveData<List<GroupDestinationMap>>()
    val groupDestination: LiveData<List<GroupDestinationMap>> get() = _groupDestination

    private val _insertGroupDestination = SingleLiveEvent<String>()
    val insertGroupDestination: LiveData<String> get() = _insertGroupDestination

    private val _deleteGroupDestination = SingleLiveEvent<String>()
    val deleteGroupDestination: LiveData<String> get() = _deleteGroupDestination

    private val _selectGroupScheduleList = MutableLiveData<MutableList<GroupSchedule>>()
    val selectGroupScheduleList: LiveData<MutableList<GroupSchedule>> get() = _selectGroupScheduleList

    private val _chatList = MutableLiveData<MutableList<Chat>>().apply { value = arrayListOf() }
    val chatList: LiveData<MutableList<Chat>> get() = _chatList

    private val _evalList =
        MutableLiveData<MutableList<EvaluationRequest>>().apply { value = arrayListOf() }
    val evalList: LiveData<MutableList<EvaluationRequest>> get() = _evalList

    private val _evalProfileList = MutableLiveData<MutableList<EvaluationProfileResponse>>()
    val evalProfileList: LiveData<MutableList<EvaluationProfileResponse>> get() = _evalProfileList

    var sharedcheck = false

    fun add(info: GroupListResponse) {
        _detailInfo.postValue(info)
    }


    fun setGroupPermission(groupPermission: Int) {
        _groupPermission.postValue(groupPermission)
    }

    fun getGroupList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectAllGroups().let { response ->
                if (response.isSuccessful) {
                    var list = response.body()!! as MutableList<GroupListResponse>
                    // list.sortBy { it.groupCreatedAt } // 최신순 정렬
                    _groupList.postValue(list)
                } else {
                    Log.d(TAG, "getGroupList : ${response.code()}")
                }

            }
        }
    }

    suspend fun selectGroupMembers(userSeq: Int, groupSeq: Int) = withContext(Dispatchers.Main) {
        repository.selectGroupMembers(groupSeq).let { response ->
            if (response.isSuccessful) {
                Log.d(TAG, "현재 유저 :${userSeq}")
                var list = response.body()!! as MutableList<Member>
                Log.d(TAG, "selectGroupMembers : ${list}")
                Log.d(TAG, "현재 유저 :${userSeq}")

                _groupPermission.value = list.find { it.userSeq == userSeq }?.memberStatus ?: 3
                _groupMemberList.postValue(list)
                true
            } else {
                Log.d(TAG, "selectGroupMembers : ${response.code()}")
                null
            }
        }
    }

    fun selectGroupDetail(groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupDetail(groupSeq).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "selectGroupDetail : ${response.code()} ${response.body()}")
                    var info = response.body()!! as GroupListResponse
                    _detailInfo.postValue(info)
                } else {
                    Log.d(TAG, "selectGroupDetail : ${response.code()}")
                }
            }

        }
    }

    fun insertGroup(groupInfo: GroupAddRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGroup(groupInfo).let { response ->
                if (response.isSuccessful) {
                    var info = response.body()!! as GroupListResponse
                    _insertGroupInfo.postValue(info)
                } else {
                    Log.d(TAG, "insertGroup: ${response.code()}")
                }
            }
        }
    }

    fun modifyGroup(groupSeq: Int, groupInfo: GroupListResponse) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.modifyGroup(groupSeq, groupInfo).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "modifyGroup: ${response.code()} ${response.body()}")
                    var info = response.body()!! as GroupListResponse
                    _detailInfo.postValue(info)
                } else {
                    Log.d(TAG, "modifyGroup: ${response.code()}")
                }
            }
        }
    }

    // 가입승인
    fun approveGroupJoin(groupSeq: Int, userSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.approveGroupJoin(groupSeq, userSeq).let { response ->
                if (response.isSuccessful) {
                    val result = response.body()!! as Boolean
                    Log.d(TAG, "approveGroupJoin: $result")
                    _approveState.postValue(result)
                    selectGroupMembers(
                        ApplicationClass.sharedPreferencesUtil.getUser(),
                        detailInfo.value?.groupSeq!!
                    )
                } else {
                    Log.d(TAG, "approveGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입요청
    fun applyGroupJoin(groupSeq: Int, groupJoin: GroupJoin) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.applyGroupJoin(groupSeq, groupJoin).let { response ->
                if (response.isSuccessful) {
                    //val result = response.body()!! as Boolean
                    Log.d(TAG, "applyGroupJoin: ${response.code()} ${response.body()}")
                    _applyState.postValue(true)
                    _groupPermission.postValue(0)
                } else {
                    Log.d(TAG, "applyGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입 취소, 탈퇴
    fun leaveGroupJoin(groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.leaveGroupJoin(groupSeq).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "leaveGroupJoin: ${response.code()},  ${response.body()}")
                    _groupPermission.postValue(3)
                } else {
                    Log.d(TAG, "leaveGroupJoin: ${response.code()}")
                }
            }
        }
    }

    // 가입 거절, 강제 퇴장
    fun outGroupJoin(groupSeq: Int, userSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.outGroupJoin(groupSeq, userSeq).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "outGroupJoin: ${response.code()},  ${response.body()}")
                    _groupPermission.postValue(3)
                    selectGroupMembers(
                        ApplicationClass.sharedPreferencesUtil.getUser(),
                        detailInfo.value?.groupSeq!!
                    )
                } else {
                    Log.d(TAG, "outGroupJoin: ${response.code()}")
                }
            }
        }
    }

    fun selectMyGroups() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectMyGroups().let { response ->
                if (response.isSuccessful) {
                    var info = response.body()!! as MutableList<MyGroupResponse>
                    Log.d(TAG, "selectMyGroups: ${response}")
                    _myGroupList.postValue(info)
                } else {
                    Log.d(TAG, "selectMyGroups: ${response.code()}")
                }
            }
        }
    }

    // 모임 목적지 조회
    fun getGroupDestination() {
        viewModelScope.launch(Dispatchers.Main) {
            Log.d(TAG, "getGroupDestination: ${_detailInfo.value?.groupSeq!!}")
            repository.getGroupDestination(_detailInfo.value?.groupSeq!!).let { response ->
                Log.d(TAG, "getGroupDestination response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "getGroupDestination response: ${response.body()}")
                    var list = response.body()!! as MutableList<GroupDestinationMap>
                    _groupDestination.postValue(list)
                } else {
                    Log.d(TAG, "insertGroupDestination: ${response.errorBody()}")
                }
            }
        }
    }

    // 모임 목적지 추가
    fun insertGroupDestination(
        groupSeq: Int,
        heritageSeq: Int,
        groupName: String,
        heritageName: String
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.insertGroupDestination(groupSeq, heritageSeq).let { response ->
                Log.d(TAG, "insertGroupDestination response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "insertGroupDestination isSuccessful: ${response.body()}")
                    if (response.body() == "Success") {
                        sharedcheck = true
                        makeToast("${groupName}에 ${heritageName}이(가) 추가되었습니다.")
                    } else {
                        sharedcheck = false
                        makeToast("이미 추가된 모임입니다")
                    }
                } else {
                    Log.d(TAG, "insertGroupDestination: ${response.code()}")
                }
            }
        }
    }

    // 모임 목적지 삭제
    fun deleteGroupDestination(
        groupSeq: Int,
        heritageSeq: Int,
        groupName: String,
        heritageName: String
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.deleteGroupDestination(groupSeq, heritageSeq).let { response ->
                Log.d(TAG, "deleteGroupDestination response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "deleteGroupDestination isSuccessful: ${response.body()}")
                    if (response.body() == "Success") {
                        makeToast("${groupName}에서 ${heritageName}이(가) 제거되었습니다.")
                    }
                } else {
                    Log.d(TAG, "deleteGroupDestination: ${response.code()}")
                }
            }
        }
    }

    fun makeToast(msg: String) {
        _message.value = Event(msg)
    }

    // 그룹 일정 등록
    fun insertGroupSchedule(groupSeq: Int, body: GroupSchedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertGroupSchedule(groupSeq, body).let { response ->
                Log.d(TAG, "insertGroupSchedule response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "insertGroupSchedule body: ${response.body()}")
                    selectGroupSchedule(detailInfo.value?.groupSeq!!)
                } else {
                    Log.d(TAG, "insertGroupSchedule: ${response.errorBody()}")
                }
            }
        }
    }

    // 그룹 일정 삭제
    fun deleteGroupSchedule(gsSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGroupSchedule(_detailInfo.value?.groupSeq!!, gsSeq).let { response ->
                Log.d(TAG, "deleteGroupSchedule response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "deleteGroupSchedule body: ${response.body()}")
                    selectGroupSchedule(detailInfo.value?.groupSeq!!)
                } else {
                    Log.d(TAG, "deleteGroupSchedule: ${response.errorBody()}")
                }
            }
        }
    }

    // 그룹 일정 조회
    fun selectGroupSchedule(groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupSchedule(groupSeq).let { response ->
                Log.d(TAG, "selectGroupSchedule response: $response")
                if (response.isSuccessful) {
                    Log.d(TAG, "selectGroupSchedule response: ${response.body()}")
                    var list = response.body()!! as MutableList<GroupSchedule>
                    _selectGroupScheduleList.postValue(list)
                } else {
                    Log.d(TAG, "selectGroupSchedule: ${response.errorBody()}")
                }
            }
        }
    }

    // 채팅 목록에 새로운 채팅 추가
    suspend fun addChat(chat: Chat) = withContext(Dispatchers.Main) {
        _chatList.value?.add(chat)
        true
    }

    // 채팅 전체 목록 불러오기
    fun getChatList(groupSeq: Int) = viewModelScope.launch {
        var response: Response<List<Chat>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllChat(_detailInfo.value?.groupSeq!!)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getChatList response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getChatList response: ${it.body()}")
                _chatList.postValue(it.body() as MutableList<Chat>?)
            } else {
                Log.d(TAG, "getChatList response: ${it.errorBody()}")

            }
        }
    }

    // 모임 상태 변경
    fun setGroupStatus(map: HashMap<String, String>) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.modifyStatus(_detailInfo.value?.groupSeq!!, map)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "setGroupStatus response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "setGroupStatus Success: ${it.body()}")

            } else {
                Log.d(TAG, "setGroupStatus error: ${it.errorBody()}")

            }
        }
    }


    // 모임 사진 업데이트
    fun updateGroupimage(img: MultipartBody.Part) = viewModelScope.launch {

        val url = sendImage(img)

        if (url != null) {
            var response: Response<String>? = null
            job = launch(Dispatchers.Main) {
                response = repository.updateGroupimage(_detailInfo.value?.groupSeq!!, url)
            }
            job?.join()

            response?.let {
                Log.d(TAG, "updateGroupimage response: $it")
                if (it.isSuccessful) {
                    Log.d(TAG, "updateGroupimage Success: ${it.body()}")
                    getGroupList()
                    selectGroupDetail(_detailInfo.value?.groupSeq!!)
                    true
                } else {
                    Log.d(TAG, "updateGroupimage error: ${it.errorBody()}")
                    false
                }
            }
        }
    }

    // 상호평가 프로필(멤버) 조회
    fun selectGroupEvaluation(groupSeq: Int, userSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupEvaluation(groupSeq, userSeq).let {
                if (it.isSuccessful) {
                    Log.d(TAG, "selectGroupEvaluation Success: ${it.body()}")
                    _evalProfileList.postValue(it.body() as MutableList<EvaluationProfileResponse>)
                } else {
                    Log.d(TAG, "selectGroupEvaluation error: ${it.errorBody()}")


                }
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
                url as String
            } else {
                null
            }
        }
    }


    // 상호평가 결과 전송
    fun insertGroupEvaluation(evalList: List<EvaluationRequest>) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.insertGroupEvaluation(evalList)

        }
        job?.join()

        response?.let {

            Log.d(TAG, "insertGroupEvaluation response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "insertGroupEvaluation Success: ${it.body()}")
            } else {
                Log.d(TAG, "insertGroupEvaluation error: ${it.errorBody()}")

            }
        }
    }
}
