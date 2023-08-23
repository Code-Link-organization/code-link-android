package com.ieee.codelink.common.extension

import com.kusu.loadingbutton.LoadingButton
fun LoadingButton.animateLoadingButton(isLoading: Boolean) {
    if (isLoading) {
        showLoading()
    } else {
        hideLoading()
    }
}