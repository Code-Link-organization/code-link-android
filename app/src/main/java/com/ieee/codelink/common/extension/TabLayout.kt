package com.ieee.codelink.common.extension

import com.google.android.material.tabs.TabLayout

fun TabLayout.provideTab(mText: String?, mId: Int?): TabLayout.Tab {
    return this.newTab().apply {
        text = mText ?: ""
        id = mId ?: 0
        contentDescription = mText

    }
}
