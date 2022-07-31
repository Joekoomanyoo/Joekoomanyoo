package com.ssafy.heritage.view.login

import androidx.navigation.fragment.NavHostFragment
import com.ssafy.heritage.R
import com.ssafy.heritage.base.BaseActivity
import com.ssafy.heritage.databinding.ActivityLoginBinding

private const val TAG = "LoginActivity___"

class LoginActivity : BaseActivity<ActivityLoginBinding>(R.layout.activity_login) {

    override fun init() {

        val navHostFragment =
            supportFragmentManager
                .findFragmentById(R.id.nav_host_login) as NavHostFragment
        val navController = navHostFragment.navController
        navController.setGraph(R.navigation.login_graph)
    }
}