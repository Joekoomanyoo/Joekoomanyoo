package com.ssafy.heritage.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.MotionEvent
import android.view.Window
import android.view.WindowInsetsController
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.*
import com.google.android.gms.location.*
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.heritage.ApplicationClass
import com.ssafy.heritage.R
import com.ssafy.heritage.RequestWorker
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.ActivityHomeBinding
import com.ssafy.heritage.listener.BackPressedListener
import com.ssafy.heritage.util.Channel.CHANNEL_ID
import com.ssafy.heritage.util.Channel.CHANNEL_NAME
import com.ssafy.heritage.viewmodel.FeedViewModel
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


private const val TAG = "HomeActivity___"
private val PERMISSIONS_REQUIRED = Manifest.permission.ACCESS_FINE_LOCATION


class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val heritageViewModel by viewModels<HeritageViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private val groupViewModel by viewModels<GroupViewModel>()
    private val feedViewModel by viewModels<FeedViewModel>()

    private lateinit var navController: NavController
    private var backButtonTime = 0L
    lateinit var backPressedListener: BackPressedListener
    var fromHeritageDetailFragment = false

    private var lat = 0.0    // 위도
    private var lng = 0.0    // 경도
    private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    lateinit var fab: FloatingActionButton

    override fun init() {
        // LocationRequest() deprecated 되서 아래 방식으로 LocationRequest 객체 생성
        // mLocationRequest = LocationRequest() is deprecated
//        mLocationRequest =  LocationRequest.create().apply {
//            interval = 100000 // 업데이트 간격 단위(밀리초)
//            fastestInterval = 100000 // 가장 빠른 업데이트 간격 단위(밀리초)
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 정확성
//            maxWaitTime= 200000 // 위치 갱신 요청 최대 대기 시간 (밀리초)
//        }
        //startLocationUpdates()
        CoroutineScope(Dispatchers.Main).launch {
            intent?.getParcelableExtra<User>("user")?.let { userViewModel.setUser(it) }



            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)

            initNavigation()

            initObserver()

            initClickListener()

            getHashKey()

            getFCMToken()

            periodicWorkRequest()

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)
            }

        }
    }


    private fun initObserver() {
        userViewModel.user.observe(this) {
            Log.d(TAG, "initObserver: $it")

            // userViewModel에 있는 user에 데이터가 들어오면 블라블라
            userViewModel.getScrapLIst()

            groupViewModel.selectMyGroups()

            groupViewModel.getGroupList()

            feedViewModel.getFeedListAll()

            ApplicationClass.sharedPreferencesUtil.saveUser(it)

        }
    }

    private fun initClickListener() = with(binding) {
        fab.setOnClickListener {
            bottomNavigation.selectedItemId = R.id.ARFragment
        }
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

    // 네비게이션 연결
    private fun initNavigation() = with(binding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main) as NavHostFragment
        navController = navHostFragment.findNavController()
        bottomNavigation.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // 바텀 네비게이션이 표시되는 Fragment
            //if(destination.id == R.id.homeFragment)
            // 바텀 네비게이션이 표시되지 않는 Fragment
        }

//        // 바텀네비 중복클릭 방지
//        bottomNavigation.setOnItemReselectedListener { }

        // 뒤로가기 버튼 리스너
        backPressedListener = object : BackPressedListener {
            override fun register() {
                fromHeritageDetailFragment = true
            }

            override fun unregister() {
                fromHeritageDetailFragment = false
            }
        }

        this@HomeActivity.fab = binding.fab
    }

    override fun onBackPressed() {
        if (fromHeritageDetailFragment) {
            super.onBackPressed()
        } else {
            val currentTime = System.currentTimeMillis()
            val gapTime = currentTime - backButtonTime
//        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
            //첫 화면(바텀 네비 화면들)이면 뒤로가기 시 앱 종료
            if (navController.currentDestination!!.id == R.id.homeFragment) {
                if (gapTime in 0..2000) {
                    //2초 안에 두 번 뒤로가기 누를 시 앱 종료
                    finish()
                } else {
                    backButtonTime = currentTime
                    Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
                }
            }
            //첫 화면(바텀 네비 화면들)이 아니면
            else {
                //Navigation의 스택에서 pop 됨(원래 동작)
                super.onBackPressed()
            }
        }
    }

    fun getFCMToken() {

        FirebaseApp.initializeApp(this)

        // FCM 토큰 수신
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }

            val token = task.result

            // token log 남기기
            Log.d(TAG, "FCMToken: ${token ?: "task.result is null"}")

            // 서버로 토큰 전송
            userViewModel.pushToken(userViewModel.user.value?.userSeq!!, token)
        })
    }

    // Notification 수신을 위한 채널 추가
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(id: String, name: String) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(id, name, importance)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    // EditText가 아닌 다른 곳 터치시 키보드 숨기기
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus

        if (view != null && view is EditText) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }

        return super.dispatchTouchEvent(ev)
    }

    // 위치 권한 체크 해주고 이후 동작 설정
    private val requestPermissionLancher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                // PERMISSION GRANTED
                getLastLocation()

                CoroutineScope(Dispatchers.Main).launch {
                    heritageViewModel.getOrderHeritage()
                }

            } else {
                // PERMISSION NOT GRANTED
                makeToast("위치 권한이 필요합니다")
            }
        }

    private fun getLastLocation() {

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLancher.launch(PERMISSIONS_REQUIRED)
            return
        }
        locationManager
            .getLastKnownLocation(LocationManager.GPS_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        locationManager
            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        locationManager
            .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
            .apply {
                if (this != null) {
                    lat = latitude
                    lng = longitude
                }
            }

        heritageViewModel.setLocation(lat.toString(), lng.toString())
    }

    private fun periodicWorkRequest() {
        //파라미터와 지연 시간 설정
        val workRequest = PeriodicWorkRequestBuilder<RequestWorker>(5, TimeUnit.MINUTES).build()
        //워크매니저 생성
        val workManager = WorkManager.getInstance(applicationContext)
        //워크매니저 실행
        workManager.enqueue(workRequest)
    }

    fun setStatusbarColor(color: String){
        when(color){
            "white" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )
                    }
                    window.statusBarColor = this.resources.getColor(com.ssafy.heritage.R.color.white)
                }
            }
            "main" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.insetsController?.setSystemBarsAppearance(
                            0,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )
                    }
                    window.statusBarColor = this.resources.getColor(com.ssafy.heritage.R.color.main_color)
                }
            }
            "trans" -> {
                if (Build.VERSION.SDK_INT >= 21) {
                    val window: Window = this.window
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        window.insetsController?.setSystemBarsAppearance(
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                            WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                        )
                    }
                    window.statusBarColor = this.resources.getColor(com.ssafy.heritage.R.color.white_100)
                }
            }
        }
    }

    private fun makeToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


//    protected fun startLocationUpdates() {
//        Log.d(TAG, "startLocationUpdates()")
//
//        //FusedLocationProviderClient의 인스턴스를 생성.
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            Log.d(TAG, "startLocationUpdates() 두 위치 권한중 하나라도 없는 경우 ")
//            return
//        }
//        Log.d(TAG, "startLocationUpdates() 위치 권한이 하나라도 존재하는 경우")
//        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
//        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청합니다.
//        mFusedLocationProviderClient!!.requestLocationUpdates(
//            mLocationRequest, mLocationCallback,
//            Looper.myLooper()!!
//        )
//    }
    // 시스템으로 부터 위치 정보를 콜백으로 받음
//    private val mLocationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            Log.d(TAG, "onLocationResult()")
//            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
//            locationResult.lastLocation
//            onLocationChanged(locationResult.lastLocation)
//        }
//    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
//    fun onLocationChanged(location: Location) {
//        Log.d(TAG, "onLocationChanged()")
//        mLastLocation = location
//        val date: Date = Calendar.getInstance().time
//        val simpleDateFormat = SimpleDateFormat("hh:mm:ss a")
//        Log.d(TAG, "onLocationChanged: ${mLastLocation.latitude}, ${mLastLocation.longitude}, ${simpleDateFormat.format(date)}")
//        //txtTime.text = "Updated at : " +  // 갱신된 날짜
////        txtLat.text = "LATITUDE : " + mLastLocation.latitude // 갱신 된 위도
////        txtLong.text = "LONGITUDE : " + mLastLocation.longitude // 갱신 된 경도
//    }

    // 위치 업데이터를 제거 하는 메서드
//    private fun stoplocationUpdates() {
//        Log.d(TAG, "stoplocationUpdates()")
//        // 지정된 위치 결과 리스너에 대한 모든 위치 업데이트를 제거
//        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
//    }

    // 위치 권한이 있는지 확인하는 메서드
    fun checkPermissionForLocation(context: Context): Boolean
    {
        Log.d(TAG, "checkPermissionForLocation()")
        // Android 6.0 Marshmallow 이상에서는 지리 확보(위치) 권한에 추가 런타임 권한이 필요합니다.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "checkPermissionForLocation() 권한 상태 : O")
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                Log.d(TAG, "checkPermissionForLocation() 권한 상태 : X")
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "onRequestPermissionsResult()")
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "onRequestPermissionsResult() _ 권한 허용 클릭")
              // startLocationUpdates()
                // View Button 활성화 상태 변경
//                btnStartupdate.isEnabled = false
//                btnStopUpdates.isEnabled = true
            } else {
                Log.d(TAG, "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this@HomeActivity, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
//    override fun onPause() {
//        super.onPause()
//        stoplocationUpdates()
//    }

}
