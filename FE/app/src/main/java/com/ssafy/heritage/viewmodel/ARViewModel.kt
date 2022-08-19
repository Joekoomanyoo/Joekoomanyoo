package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.data.dto.StampCategory
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.remote.request.NearStampRequest
import com.ssafy.heritage.data.remote.response.StampRankResponse
import com.ssafy.heritage.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Response

private const val TAG = "ARViewModel___"

class ARViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    private val _stampList = MutableLiveData<MutableList<Stamp>>()
    val stampList: LiveData<MutableList<Stamp>>
        get() = _stampList

    private val _categoryList = MutableLiveData<List<StampCategory>>()
    val categoryList: LiveData<List<StampCategory>>
        get() = _categoryList

    private val _myStampList = MutableLiveData<MutableList<Stamp>>()
    val myStampList: LiveData<MutableList<Stamp>>
        get() = _myStampList

    private val _nearMyStampList = MutableLiveData<MutableList<Stamp>>()
    val nearMyStampList: LiveData<MutableList<Stamp>>
        get() = _nearMyStampList

    private val _myCategoryList = MutableLiveData<List<StampCategory>>()
    val myCategoryList: LiveData<List<StampCategory>>
        get() = _myCategoryList

    private val _stampRankList = MutableLiveData<MutableList<StampRankResponse>>()
    val stampRankList: LiveData<MutableList<StampRankResponse>>
        get() = _stampRankList

    // 전체 스탬프 목록 불러오기
    fun getAllStamp() = viewModelScope.launch {
        var response: Response<List<Stamp>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectAllStamp()
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getMyStamp response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getMyStamp body: ${it.body()}")
                _stampList.value = it.body() as MutableList<Stamp>?
            } else {
                Log.d(TAG, "getMyStamp: ${it.errorBody()}")
            }
        }
    }

    // 스탬프 카테고리별 개수 불러오기
    fun getStampCategory() = viewModelScope.launch {
        var response: Response<List<StampCategory>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectStampCategory()
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getStampCategory response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getStampCategory Success: ${it.body()}")
                val list = it.body()
                val newList = list?.filter { it.categoryCnt > 0 }
                _categoryList.postValue(newList!!)
            } else {
                Log.d(TAG, "getStampCategory error: ${it.errorBody()}")

            }
        }
    }
    // 내가 보유한 스탬프 불러오기
    fun getMyStamp(userSeq: Int) = viewModelScope.launch {
        var response: Response<List<Stamp>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.getMyStamp(userSeq)
        }
        job?.join()

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
    
    // 획득한 스탬프 등록
    fun addStamp(userSeq: Int, stampSeq: Int ) = viewModelScope.launch {
        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.addStamp(userSeq, stampSeq)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "addStamp response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "addStamp body: ${it.body()}")

                // 새로 등록하고 다시 내 스탬프 목록 갱신
                getMyStamp(userSeq)
            } else {
                Log.d(TAG, "addStamp: ${it.errorBody()}")
            }
        }
    }

    // 실시간 내 위치 전송(내 위치를 전송하여 나와 가까운 스탬프 정보 조회)
    fun selectNearStamp(location: NearStampRequest){
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectNearStamp(location).let { response ->
                if (response.isSuccessful) {
                    val list = response.body() as MutableList<Stamp>
                    _nearMyStampList.postValue(list)
                    Log.d(TAG, "selectNearStamp: ${response.code()}")
                }else{
                    Log.d(TAG, "selectNearStamp: ${response.code()}")
                }
            }
        }
    }
    // 나의 스탬프 카테고리별 개수 불러오기
    fun selectMyStampCategory(userSeq: Int, categorySeq: Int)= viewModelScope.launch {
        var response: Response<List<StampCategory>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectMyStampCategory(userSeq, categorySeq)
        }
        job?.join()

        response?.let {
            Log.d(TAG, "getMyStampCategory response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "getMyStampCategory Success: ${it.body()}")
                val list = it.body()
                val newList = list?.filter { it.categoryCnt > 0 }
                _myCategoryList.postValue(newList!!)
            } else {
                Log.d(TAG, "getMyStampCategory error: ${it.errorBody()}")

            }
        }
    }

    fun selectStampRank() =viewModelScope.launch {
        var response: Response<List<StampRankResponse>>? = null
        job = launch(Dispatchers.Main) {
            response = repository.selectStampRank()
        }
        job?.join()
        response?.let {
            Log.d(TAG, "selectStampRank response: $it")
            if (it.isSuccessful) {
                Log.d(TAG, "selectStampRank Success: ${it.body()}")
                val list = it.body() as MutableList<StampRankResponse>
                _stampRankList.postValue(list)
            } else {
                Log.d(TAG, "selectStampRank error: ${it.errorBody()}")

            }
        }
    }
}