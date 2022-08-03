package com.ssafy.heritage.view.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ssafy.heritage.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityLoginBinding
import com.ssafy.heritage.util.JWTUtils
import com.ssafy.heritage.view.HomeActivity

private const val TAG = "LoginActivity___"

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    private lateinit var navController: NavController
    private var backButtonTime = 0L

    override fun init() {

        autoLogin()

        initNavigation()
    }

    private fun autoLogin() {
        val token = sharedPreferencesUtil.getToken()

        Log.d(TAG, "autoLogin token: $token")

        // 저장된 토큰 정보가 있다면 로그인 실행
        if (token != null) {
            val user = JWTUtils.decoded(token)
            Log.d(TAG, "autoLogin user: $user")

            if (user != null) {
                Intent(this, HomeActivity::class.java).apply {
                    putExtra("user", user)
                    startActivity(this)
                    finish()
                }
            }
        }
    }

    private fun initNavigation() {
        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_login) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.login_graph)
    }

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val gapTime = currentTime - backButtonTime
//        val currentFragment = supportFragmentManager.findFragmentById(galleryFragment.id)
        //첫 화면(바텀 네비 화면들)이면 뒤로가기 시 앱 종료
        if (navController.currentDestination!!.id == R.id.loginFragment ||
            navController.currentDestination!!.id == R.id.joinFragment
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