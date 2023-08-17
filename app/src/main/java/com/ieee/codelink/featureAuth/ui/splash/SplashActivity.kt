package com.ieee.codelink.featureAuth.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.ieee.codelink.core.BaseActivity
import com.ieee.codelink.featureAuth.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.featureAuth.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TAG = "SplashActivity"

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    lateinit var sharedPreferenceManger: SharedPreferenceManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (sharedPreferenceManger.hasLoggedIn)
            //todo: go to mainScreen
          //  startActivity(Intent(this, MainActivity::class.java))
        else
            startActivity(Intent(this, AuthActivity::class.java))
    }
}