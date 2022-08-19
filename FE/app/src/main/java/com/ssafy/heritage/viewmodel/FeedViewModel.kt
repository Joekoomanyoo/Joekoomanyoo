package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.remote.request.FeedAddRequest
import com.ssafy.heritage.data.remote.response.FeedListResponse
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject
import retrofit2.Response

private const val TAG = "FeedViewModel___"

class FeedViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    private val _reviewState = SingleLiveEvent<Boolean>()
    val reviewState: LiveData<Boolean> get() = _reviewState

    private val _myFeedList = MutableLiveData<List<FeedListResponse>>()
    val myFeedList: MutableLiveData<List<FeedListResponse>> get() = _myFeedList

    private val _feedListAll = MutableLiveData<List<FeedListResponse>>()
    val feedListAll: MutableLiveData<List<FeedListResponse>> get() = _feedListAll

    private val _feedListByHashTag = MutableLiveData<List<FeedListResponse>>()
    val feedListByHashTag: MutableLiveData<List<FeedListResponse>> get() = _feedListByHashTag

    private val _insertFeedInfo = SingleLiveEvent<String>()
    val insertFeedInfo: LiveData<String> get() = _insertFeedInfo

    private val _feedInfodetail = MutableLiveData<FeedListResponse>()
    val feedInfoDetailL: LiveData<FeedListResponse> get() = _feedInfodetail

    private val _feedOpen = SingleLiveEvent<String>()
    val feedOpen: LiveData<String> get() = _feedOpen

    private val _feedCount = SingleLiveEvent<Int>()
    val feedCount: LiveData<Int> get() = _feedCount

    fun add(info: FeedListResponse) {
        _feedInfodetail.postValue(info)
    }

    fun getMyFeedList() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectMyFeeds().let { response ->
                if (response.isSuccessful) {
                    var list = response.body() as MutableList<FeedListResponse>
                    _feedListAll.postValue(list)
                    Log.d(TAG, "getMyFeedList: 내 피드 목록 조회 성공")
                } else {
                    Log.d(TAG, "getMyFeedList: ${response.code()}")
                }
            }
        }
    }

    fun getFeedListByHashTag(fhTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectFeedsByHashtag(fhTag).let { response ->
                if (response.isSuccessful) {
                    var list = response.body() as MutableList<FeedListResponse>
                    _feedListAll.postValue(list)
                } else {
                    Log.d(TAG, "getFeedListByHashTag: ${response.code()}")
                }
            }
        }
    }

    fun getFeedListAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectAllFeeds().let { response ->
                if (response.isSuccessful) {
                    var list = response.body() as MutableList<FeedListResponse>
                    _feedListAll.postValue(list)
                    Log.d(TAG, "getFeedListAll: 모든 피드 목록 조회 성공")
                } else {
                    Log.d(TAG, "getFeedListAll: ${response.code()}")
                }
            }
        }
    }

    fun insertFeed(feedInfo: FeedAddRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFeed(feedInfo).let { response ->
                if (response.isSuccessful) {
//                    var info = response.body()
//                    _insertFeedInfo.postValue(info)
                    Log.d(TAG, "insertFeed: 피드에 글 들어갔다!")
                } else {
                    Log.d(TAG, "insertFeed: ${response.code()}")
                }
            }
        }
    }

    fun deleteFeed(feedSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFeed(feedSeq).let { response ->
                if (response.isSuccessful) {
                    Log.d(TAG, "deleteFeed: 피드 삭제")
                } else {
                    Log.d(TAG, "deleteFeed: ${response.code()}")
                }
            }
        }
    }

    fun getFeedHashTag(fhTag: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectFeedHashTag(fhTag).let { response ->
                if (response.isSuccessful) {
                    var list = response.body() as MutableList<FeedListResponse>
                    _feedListByHashTag.postValue(list)
                    Log.d(TAG, "getFeedListAll: 해시태그 가져오기")
                } else {
                    Log.d(TAG, "getFeedListAll: ${response.code()}")
                }
            }
        }
    }

    fun changeFeedOpen(feedSeq: Int, feedOpen: Char) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.changeFeedOpen(feedSeq, feedOpen).let { response ->
                if (response.isSuccessful) {
                    val result = response.body() as String
                    Log.d(TAG, "changeFeedOpen: $result")
                    _feedOpen.postValue(result)
                } else {
                    Log.d(TAG, "changeFeedOpen: ${response.code()}")
                }
            }
        }
    }

    // 사진 전송하기
    suspend fun sendImage(img: MultipartBody.Part) = withContext(Dispatchers.Main) {

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
                _insertFeedInfo.value = url as String
                Log.d(TAG, "sendImage: ${url as String}")
                Log.d(TAG, "sendImage: ${_insertFeedInfo.value}")
                true
            } else {
                false
            }
        }
    }

    fun insertFeedLike(feedSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) { 
            repository.insertFeedLike(feedSeq).let { response ->
                Log.d(TAG, "insertFeedLike: ${response}")
                if (response.isSuccessful) {
                    countFeedLike(feedSeq)
                    Log.d(TAG, "insertFeedLike: 좋아요 성공")
                    Log.d(TAG, "insertFeedLike: ${response.body()}")
                } else {
                    Log.d(TAG, "insertFeedLike: ${response.errorBody()}")
                }
            }
        }
    }

    fun deleteFeedLike(feedSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFeedLike(feedSeq).let { response ->
                Log.d(TAG, "deleteFeedLike: $response")
                if (response.isSuccessful) {
                    countFeedLike(feedSeq)
                    Log.d(TAG, "insertFeedLike: 좋아요 취소 성공")
                    Log.d(TAG, "deleteFeedLike: ${response.body()}")
                } else {
                    Log.d(TAG, "insertFeedLike: ${response.errorBody()}")
                }
            }
        }
    }

    fun countFeedLike(feedSeq: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.countFeedLike(feedSeq).let { response ->
                if (response.isSuccessful) {
                    var temp = response.body() as Int
                    _feedCount.postValue(temp)
                    Log.d(TAG, "countFeedLike: ${response.body()}")
                } else {
                    Log.d(TAG, "countFeedLike: ${response.errorBody()}")
                }
            }
        }
    }

}