package com.ieee.codelink.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.ieee.codelink.R
import com.ieee.codelink.common.showToast
import com.ieee.codelink.core.BaseActivity
import com.ieee.codelink.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import np.com.susanthapa.curved_bottom_navigation.BottomNavItemView
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
      //  window.statusBarColor = Color.TRANSPARENT
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            initNavHost()
            binding.bottomNavigation.setMenuItems(menuItems, 0)
            binding.bottomNavigation.setupWithNavController(navController)
            setContentView(root)
        }
        setUpVisibilityOfBottomBar()
        setUpKeyBoardRules()
        checkIfSettingsChanged()


    }

    private fun checkIfSettingsChanged() {
        if (intent.getStringExtra("target") == "profile"){
            //todo Navigate to profile
            //navController.navigate(R.id.profileFragment)
            //binding.bottomNavigation.invalidate()
        }
    }

    private fun initNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private val menuItems = arrayOf(
        CbnMenuItem(
            R.drawable.ic_home2,
            R.drawable.avd_home2,
            R.id.homeFragment
        ),
        CbnMenuItem(
            R.drawable.ic_search,
            R.drawable.avd_search,
            R.id.searchFragment
        ),
        CbnMenuItem(
            R.drawable.ic_chat2,
            R.drawable.avd_chat2,
            R.id.chatsFragment
        )
        , CbnMenuItem(
            R.drawable.ic_profile,
            R.drawable.avd_profile,
            R.id.profileFragment
        )
    )

    private fun setUpVisibilityOfBottomBar() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.homeFragment,
                R.id.searchFragment,
                R.id.profileFragment,
                R.id.chatsFragment
                -> {
                    bottomBarNavigationVisibility(true)
                }
                else -> {
                    bottomBarNavigationVisibility(false)
                }
            }
        }
    }

    private fun setUpKeyBoardRules() {
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.searchFragment,
                -> {
                    disableKeyBoardPushingViews()
                }
                else -> {
                    enableKeyBoardPushingViews()
                }
            }
        }
    }
    private fun bottomBarNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible
    }

    fun saveSettings() {
        restart()
    }


    fun restart() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("target", "profile")
        this.finish()
        this.overridePendingTransition(0, 0)
        this.startActivity(intent)
        this.overridePendingTransition(0, 0)
    }

    fun resetNavGraph() {
        val currentGraph = navController.graph
        val inflater = navController.navInflater
        val newGraph = inflater.inflate(R.navigation.main_nav)
        newGraph.addAll(currentGraph)
        newGraph.setStartDestination(R.id.homeFragment)
        navController.graph = newGraph
    }

    private fun disableKeyBoardPushingViews() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
    }
    private fun enableKeyBoardPushingViews() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

}