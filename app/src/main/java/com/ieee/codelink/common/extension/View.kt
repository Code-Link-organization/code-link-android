package com.ieee.codelink.common.extension

import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.toggle() {
    if (this.isVisible) this.gone()
    else this.show()
}

fun View.enable() {
    this.isEnabled = true
}

fun View.disable() {
    this.isEnabled = false
}

fun View.setMargin(
    marginLeft: Int = 0,
    marginTop: Int = 0,
    marginRight: Int = 0,
    marginBottom: Int = 0,
) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(marginLeft, marginTop, marginRight, marginBottom)
    this.layoutParams = menuLayoutParams
}


/**Use this instead of setOnclickListener to make a delay in setOnclickListener method
to avoid crashes in the app when the user clicks too much on any clickable view **/
/** throttleTime : the delay time in the click (default =300L)
acton : the action to be preformed in the click**/
fun View.clickWithThrottle(throttleTime: Long = 300L, action: () -> Unit) {
    this.setOnClickListener(object : View.OnClickListener {

        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < throttleTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
