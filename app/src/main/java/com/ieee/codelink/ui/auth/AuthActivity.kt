package com.ieee.codelink.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseActivity
import com.ieee.codelink.databinding.ActivityAuthBinding
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.ui.main.MainActivity
import com.zeugmasolutions.localehelper.Locales
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityAuthBinding
    private lateinit var graph: NavGraph
    private lateinit var splashScreen: SplashScreen

    @Inject
    lateinit var sharedPreferences: SharedPreferenceManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setSavedLanguage()
        setAppDarkMode()

        binding = ActivityAuthBinding.inflate(layoutInflater)
        splashScreen = installSplashScreen()
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val inflater = navHostFragment.navController.navInflater
        graph = inflater.inflate(R.navigation.auth_navigation)

        if (sharedPreferences.hasLoggedIn){
            goToMainScreen()
        }

        when {
            sharedPreferences.isOnboardingFinished -> {
                graph.setStartDestination(R.id.loginFragment)
            }

            else -> {
                graph.setStartDestination(R.id.onBoardingFragment)
            }
        }

        navController = navHostFragment.findNavController()
        navHostFragment.navController.graph = graph

    }

    private fun setAppDarkMode() {
        val isDarkMode = sharedPreferences.getBooleanValue(
            SharedPreferenceManger.DARK_MODE,
            false
        )
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setSavedLanguage() {
        if (getCurrentLanguage() == "Arabic"){
            updateLocale(Locales.Arabic)
        }else{
            updateLocale(Locales.English)
        }
    }

    private fun getCurrentLanguage(): String = sharedPreferences.getStringValue(
        SharedPreferenceManger.CURRENT_LANGUAGE,
        "English"
    )

    private fun goToMainScreen() {
        val intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}