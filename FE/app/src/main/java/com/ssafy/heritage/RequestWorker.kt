package com.ssafy.heritage

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.ar.core.dependencies.e
import com.ssafy.heritage.data.dto.Stamp
import com.ssafy.heritage.data.remote.request.NearStampRequest
import com.ssafy.heritage.data.repository.Repository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

private const val TAG = "RequestWorker___"
@SuppressLint("MissingPermission")
class RequestWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    private val repository = Repository.get()
    private val _nearMyStampList = MutableLiveData<MutableList<Stamp>>()
    val nearMyStampList: LiveData<MutableList<Stamp>>
        get() = _nearMyStampList
    private var lat = 0.0    // 이전 위도
    private var lng = 0.0    // 이전 경도
    private val mContext = context
    private val userSeq: Int = ApplicationClass.sharedPreferencesUtil.getUser()

    //위치 가져올때 필요
    private val mFusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(mContext)
    }

    override fun doWork(): Result{
        try{
            //마지막 위치를 가져오는데에 성공한다면
            mFusedLocationClient.lastLocation.addOnCompleteListener { task ->
                if( task.isSuccessful){
                    task.result?.let{ aLocation ->

                        GlobalScope.launch {
                            repository.selectNearStamp(NearStampRequest(userSeq, aLocation.latitude.toString(), aLocation.longitude.toString())).let { response ->
                                Log.d(TAG, "doWork: ${response}")
                                if (response.isSuccessful) {
                                    val list = response.body() as MutableList<Stamp>
                                    _nearMyStampList.postValue(list)
                                    Log.d(TAG,"doWork내 현재 위치 : ${aLocation.latitude}, ${aLocation.longitude}" )
                                    Log.d(TAG, "selectNearStamp: ${response.code()} ${response.body()}")
                                    if(list.size != 0){
                                        Log.d(TAG, "doWork__: $list")
                                        ApplicationClass.sharedPreferencesUtil.saveStamp(list.get(0))
                                        Log.d(TAG, "doWork___: ${ApplicationClass.sharedPreferencesUtil.getStamp()}")
                                    }
                                }else{
                                    Log.d(TAG, "selectNearStamp: ${response.code()}")
                                }
                            }
                        }
                    }
                }else{
                    Log.e(TAG, "TASK 실패")
                }
            }
            return Result.success()
        }catch (e:Exception){
            return Result.failure()
        }
    }
}