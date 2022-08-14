package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.Chat
import com.ssafy.heritage.data.dto.Member
import com.ssafy.heritage.data.remote.request.GroupAddRequest
import com.ssafy.heritage.data.remote.request.GroupBasic
import com.ssafy.heritage.data.remote.request.GroupJoin
import com.ssafy.heritage.data.remote.request.GroupSchedule
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.remote.response.MyGroupResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.event.Event
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val _insertGroupDestination = SingleLiveEvent<String>()
    val insertGroupDestination: LiveData<String> get() = _insertGroupDestination

    private val _deleteGroupDestination = SingleLiveEvent<String>()
    val deleteGroupDestination: LiveData<String> get() = _deleteGroupDestination

    private val _selectGroupScheduleList = SingleLiveEvent<MutableList<GroupSchedule>>()
    val selectGroupScheduleList: LiveData<MutableList<GroupSchedule>> get() = _selectGroupScheduleList

    private val _chatList = MutableLiveData<MutableList<Chat>>().apply { value = arrayListOf() }
    val chatList: LiveData<MutableList<Chat>> get() = _chatList

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
                }else{
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
    fun outGroupJoin(groupSeq: Int, userSeq: Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.outGroupJoin(groupSeq, userSeq).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "outGroupJoin: ${response.code()},  ${response.body()}")
                    _groupPermission.postValue(3)
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
                if (response.isSuccessful) {
                    if (response.body() == "Success") {
                        Log.d(TAG, "insertGroupSchedule - ${response.body()}: 일정이 등록되었습니다")
                    } else {
                        Log.d(TAG, "insertGroupSchedule - ${response.body()}: 이미 등록된 일정입니다")
                    }
                } else {
                    Log.d(TAG, "insertGroupSchedule: ${response.code()}")
                }
            }
        }
    }

    // 그룹 일정 삭제
    fun deleteGroupSchedule(groupSeq: Int, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteGroupSchedule(groupSeq, date).let { response ->
                if (response.isSuccessful) {
                    if (response.body() == "Success") {
                        Log.d(TAG, "deleteGroupSchedule - ${response.body()}: 일정이 삭제되었습니다")
                    } else {
                        Log.d(TAG, "deleteGroupSchedule - ${response.body()}: 이미 삭제된 일정입니다")
                    }
                } else {
                    Log.d(TAG, "deleteGroupSchedule: ${response.code()}")
                }
            }
        }
    }

    // 그룹 일정 조회
    fun selectGroupSchedule(groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupSchedule(groupSeq).let { response ->
                if (response.isSuccessful) {
                    var list = response.body()!! as MutableList<GroupSchedule>
                    _selectGroupScheduleList.postValue(list)
                } else {
                    Log.d(TAG, "selectGroupSchedule: ${response.code()}")
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
}