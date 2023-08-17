package com.ieee.codelink.common.extension

import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat


fun Activity.hideKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    // Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = currentFocus
    // If no view currently has focus, create a new one, just so we can grab a window token from it.
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.showKeyboard() {
    val inputMethodManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    // Find the currently focused view, so we can grab the correct window token from it.
    var view: View? = currentFocus
    // If no view currently has focus, create a new one, just so we can grab a window token from it.
    if (view == null) {
        view = View(this)
    }
    inputMethodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.showContentAboveStatusBar(color: Int = -1) {
    try {
        window.apply {
            statusBarColor = if (color != -1) Color.TRANSPARENT else color
            setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            )
        }
    } catch (e: Exception) {
        Log.e("ActivityExtensions", "showContentAboveStatusBar: ${e.localizedMessage}")
    }
}

fun Activity.showContentNormallyUnderStatusBar(color: Int) {
    window.apply {
        statusBarColor = ContextCompat.getColor(this@showContentNormallyUnderStatusBar, color)
        clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}