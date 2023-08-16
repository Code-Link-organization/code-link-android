package com.ieee.codelink.common.extension

import android.widget.ImageView
import com.varunest.sparkbutton.SparkButton
import com.varunest.sparkbutton.SparkEventListener

fun SparkButton.setOnCustomClickListener(action: (isChecked: Boolean) -> Unit) {
    this.setEventListener(object : SparkEventListener {
        override fun onEvent(button: ImageView?, buttonState: Boolean) {
            action(buttonState)
        }

        override fun onEventAnimationEnd(button: ImageView?, buttonState: Boolean) {}
        override fun onEventAnimationStart(button: ImageView?, buttonState: Boolean) {}
    })
}