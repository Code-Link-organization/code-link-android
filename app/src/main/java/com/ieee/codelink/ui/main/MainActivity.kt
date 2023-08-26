package com.ieee.codelink.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.ieee.codelink.R
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPref : SharedPreferenceManger
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv_logout).setOnClickListener {
            sharedPref.logout()
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}