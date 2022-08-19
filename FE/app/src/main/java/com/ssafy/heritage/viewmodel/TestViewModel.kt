package com.ssafy.heritage.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.heritage.data.repository.Repository
import com.ssafy.heritage.util.FormDataUtil
import com.ssafy.heritage.util.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

private const val TAG = "TestViewModel___"

class TestViewModel : ViewModel() {

    private val repository = Repository.get()
    private val _reviewState = SingleLiveEvent<Boolean>()
    val reviewState: LiveData<Boolean>
        get() = _reviewState

    fun saveImage(file: File) {
//        val img = FormDataUtil.getImageBody("file", file)
//        viewModelScope.launch(Dispatchers.Main) {
//            repository.saveImage(
//                img
//            ).let { response ->
//                Log.d(TAG, "saveImage: ${response.body()}")
//                if (response.isSuccessful) {
//                    _reviewState.postValue(response.body())
//                    Log.d(TAG, "성공uploadChat: ${response.code()}")
//                } else {
//                    _reviewState.postValue(response.body())
//                    Log.e("uploadChat()", "에러 : " + response.body())
//                    Log.d(TAG, "실패uploadChat: ${response.code()}")
//                }
//            }
//        }
    }
}