package com.ieee.eatme.common.extension

import android.util.Log

fun tryNow(
    tag: String = "TryNowError",
//    error: ((Exception) -> Unit)? = null,
    action: () -> Unit,
) {
    try {
        action()
    } catch (e: Exception) {
        Log.e(tag, e.localizedMessage ?: "Unknown")
    }
}
