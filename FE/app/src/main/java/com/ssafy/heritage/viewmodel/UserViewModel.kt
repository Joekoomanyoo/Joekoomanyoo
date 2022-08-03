package com.ssafy.heritage.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class UserViewModel : ViewModel() {

    var job: Job? = null

    private val repository = Repository.get()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun setUser(user: User) {
        _user.value = user
    }

    // 회원 탈퇴
    suspend fun withdrawal() = withContext(Dispatchers.Main) {

        var response: Response<String>? = null
        job = launch(Dispatchers.Main) {
            response = repository.resign(user.value!!.userId)
        }
        job?.join()

        response?.let {
            it.isSuccessful
        }
    }
}