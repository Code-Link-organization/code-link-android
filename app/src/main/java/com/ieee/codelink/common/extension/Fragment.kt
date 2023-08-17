package com.ieee.codelink.common.extension


import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

fun Fragment.hideKeyboard() {
    requireActivity().hideKeyboard()
}

fun Fragment.showKeyboard() {
    requireActivity().showKeyboard()
}


fun Fragment.navigateToAction(
    action: NavDirections,
    popBackToDestination: Int = -1,
    isInclusive: Boolean = false,
    saveState: Boolean = false,
    haveAnimation: Boolean = true,
    navOptions: NavOptions.Builder = NavOptions.Builder(),
) {

    try {
        if (!haveAnimation) {
            findNavController().navigate(action, navOptions.build())
            return
        }

        navOptions.apply {
            setEnterAnim(android.R.anim.fade_in)
            setPopEnterAnim(android.R.anim.fade_in)
            if (popBackToDestination != -1) setPopUpTo(popBackToDestination, isInclusive, saveState)
        }

        findNavController().navigate(action, navOptions.build())
    } catch (e: Exception) {
        Log.e("navigateToAction", e.localizedMessage ?: "navigateToAction Unknown error")
    }
}

fun Fragment.onBackPress(action: () -> Unit) {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                action()
            }
        })
}