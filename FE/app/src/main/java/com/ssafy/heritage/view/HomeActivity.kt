package com.ssafy.heritage.view

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ssafy.heritage.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.data.dto.User
import com.ssafy.heritage.databinding.ActivityHomeBinding
import com.ssafy.heritage.viewmodel.GroupViewModel
import com.ssafy.heritage.viewmodel.HeritageViewModel
import com.ssafy.heritage.viewmodel.UserViewModel
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


private const val TAG = "HomeActivity___"

class HomeActivity : BaseActivity<ActivityHomeBinding>(R.layout.activity_home) {

    private val heritageViewModel by viewModels<HeritageViewModel>()
    private val userViewModel by viewModels<UserViewModel>()
    private lateinit var navController: NavController
    private var backButtonTime = 0L

    override fun init() {
        intent?.getParcelableExtra<User>("user")?.let { userViewModel.setUser(it) }

        initNavigation()

        initObserver()

        getHashKey()
    }

    override fun onStart() {
        super.onStart()
        heritageViewModel.getHeritageList()
    }

    private fun initObserver() {
        userViewModel.user.observe(this) {
            Log.d(TAG, "initObserver: $it")
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
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val gapTime = currentTime - backButtonTime
//        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
        //첫 화면(바텀 네비 화면들)이면 뒤로가기 시 앱 종료
        if (navController.currentDestination!!.id == R.id.groupListFragment ||
            navController.currentDestination!!.id == R.id.heritageListFragment ||
            navController.currentDestination!!.id == R.id.homeFragment ||
            navController.currentDestination!!.id == R.id.feedListFragment ||
            navController.currentDestination!!.id == R.id.ARFragment
        ) {
            if (gapTime in 0..2000) {
                //2초 안에 두 번 뒤로가기 누를 시 앱 종료
                finishAndRemoveTask()
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