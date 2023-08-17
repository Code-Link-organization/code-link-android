package com.ieee.codelink.common.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.content.res.use

fun Context.hideKeyboard(view: View) {
    val imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}


fun Context.getResourceColor(color: Int): Int = ContextCompat.getColor(this, color)
fun Context.getResourceDrawable(@DrawableRes resourceId: Int): Drawable? =
    ContextCompat.getDrawable(this, resourceId)


fun Context.getResourceDrawableIfNotNull(@DrawableRes resourceId: Int?): Drawable? =
    if (resourceId == null) null
    else ContextCompat.getDrawable(this, resourceId)

@ColorInt
fun Context.getThemeColor(@AttrRes themeAttrId: Int): Int {
    return obtainStyledAttributes(intArrayOf(themeAttrId)).use {
        it.getColor(0, Color.MAGENTA)
    }
}

@ColorInt
fun Context.getColorCompact(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.toast(@StringRes textRes: Int) {
    Toast.makeText(this, getString(textRes), Toast.LENGTH_SHORT).show()
}

fun Context.showKeyboard(editText: EditText) {
    val inputMethodManager: InputMethodManager =
        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}
