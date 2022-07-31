package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.GroupAttribute
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.remote.response.GroupListResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "GroupViewModel___"

class GroupViewModel: ViewModel() {

    private val repository = Repository.get()

    private val _groupList = SingleLiveEvent<MutableList<GroupListResponse>>()
    val groupList: LiveData<MutableList<GroupListResponse>> get() = _groupList

    private val _groupDetailInfo = SingleLiveEvent<GroupAttribute>()
    val groupDetailInfo: LiveData<GroupAttribute> get() = _groupDetailInfo

    private val _groupMemberList = SingleLiveEvent<MutableList<User>>()
    val groupMemberList: LiveData<MutableList<User>> get() = _groupMemberList

    fun getGroupList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectAllGroups().let { response ->
                if(response.isSuccessful){
                    var list = response.body()!! as MutableList<GroupListResponse>
                   // list.sortBy { it.groupCreatedAt } // 최신순 정렬
                    _groupList.postValue(list)
                }else{
                    Log.d(TAG, "${response.code()}")
                }

            }
        }
    }

    fun getGroupDetailInfo(groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupDetail(groupSeq).let { response ->
                if(response.isSuccessful){
                    var info = response.body()!! as GroupAttribute
                    _groupDetailInfo.postValue(info)
                }else{
                    Log.d(TAG, "${response.code()}")
                }
            }
        }
    }

    fun selectGroupMembers(groupSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectGroupMembers(groupSeq).let { response ->
                if(response.isSuccessful) {
                    var list = response.body()!! as MutableList<User>
                    _groupMemberList.postValue(list)
                }else{
                    Log.d(TAG, "${response.code()}")
                }
            }
        }
    }
}