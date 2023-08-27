package com.ieee.codelink.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ieee.codelink.R
import com.ieee.codelink.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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

    }

    private fun initNavHost() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private val menuItems = arrayOf(
        CbnMenuItem(
            R.drawable.ic_home,
            R.drawable.avd_home,
            R.id.homeFragment
        ),
        CbnMenuItem(
            R.drawable.ic_search,
            R.drawable.avd_search,
            R.id.searchFragment
        ),
        CbnMenuItem(
            R.drawable.ic_chat,
            R.drawable.avd_chat,
            R.id.chatsFragment
        ), CbnMenuItem(
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
                -> bottomBarNavigationVisibility(true)

                else -> bottomBarNavigationVisibility(false)
            }
        }
    }

    private fun bottomBarNavigationVisibility(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible
    }
}