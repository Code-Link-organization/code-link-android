package com.ieee.codelink.common.extension

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.onPageSelected(onPageSelected: (position: Int) -> Unit) {
    this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            onPageSelected(position)
        }
    })
}