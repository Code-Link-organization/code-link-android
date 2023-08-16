package com.ieee.codelink.common.extension

import android.content.res.Resources
import androidx.fragment.app.Fragment

val Float.pxToDp get() = this / Resources.getSystem().displayMetrics.density
val Float.dpToPx get() = this * Resources.getSystem().displayMetrics.density

val Int.dpToPx get() = this * Resources.getSystem().displayMetrics.density.toInt()

val Int.pxToDp get() = this / Resources.getSystem().displayMetrics.density.toInt()

val Float.dpToSp get() = this / Resources.getSystem().displayMetrics.scaledDensity

val Fragment.width get() = requireContext().resources.displayMetrics.widthPixels
val Fragment.height get() = requireContext().resources.displayMetrics.heightPixels
