package com.ieee.codelink.featureAuth.ui.auth

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseActivity
import com.ieee.codelink.databinding.ActivityAuthBinding
import com.ieee.codelink.featureAuth.data.local.preference.SharedPreferenceManger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityAuthBinding
    private lateinit var graph: NavGraph


    @Inject
    lateinit var sharedPreferences: SharedPreferenceManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //todo: setNavigation logic
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        val inflater = navHostFragment.navController.navInflater
        graph = inflater.inflate(R.navigation.auth_navigation)

        when {
            sharedPreferences.openedTheAppBefore -> {
                //todo : navigate to login from inBoarding
          //      graph.setStartDestination(R.id.loginFragment)
            }
            else -> {
                graph.setStartDestination(R.id.onBoardingFragment)
            }
        }

        navController = navHostFragment.findNavController()
        navHostFragment.navController.graph = graph

    }
}