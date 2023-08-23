package com.ieee.codelink.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexon.Akelni_station.common.network.globalNetworkCall
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    val error = MutableSharedFlow<String?>()

    protected fun < T : BaseResponse> networkCall(
        action: suspend () -> Response<T>,
        onReply: ((ResponseState<T>) -> Unit)? = null,
        doAfter: (suspend (isSuccess: Boolean) -> Unit)? = null,
        showLoading: Boolean = true,
    ): Job {
        return globalNetworkCall(
            action = action,
            onReply = onReply,
            doAfter = doAfter,
            showLoading = showLoading,
            scope = viewModelScope
        )
    }
}