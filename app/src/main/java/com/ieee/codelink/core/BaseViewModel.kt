package com.ieee.codelink.core

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ieee.codelink.common.network.globalNetworkCall
import com.ieee.codelink.common.parseErrorMessage
import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import com.ieee.codelink.data.fakeDataProvider.FakeProvider
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import retrofit2.Response

open class BaseViewModel : ViewModel() {

    val error = MutableSharedFlow<String?>()

    protected fun <T : BaseResponse> networkCall(
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

    fun <T> handleResponse(response: Response<T>?): ResponseState<T> {
        if (response?.isSuccessful == true) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        if (response == null) {
            return ResponseState.NetworkError()
        }

        if (response.code() == 401) {
            return ResponseState.NotAuthorized()
        }

        val errorBody = response.errorBody()?.string()
        val errorMessage = parseErrorMessage(errorBody)
        return ResponseState.Error(errorMessage , null)
    }

}