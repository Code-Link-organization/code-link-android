package com.alexon.Akelni_station.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.ResponseState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


fun <T : BaseResponse> globalNetworkCall(
    action: suspend () -> Response<T>,
    onReply: ((ResponseState<T>) -> Unit)? = null,
    doAfter: (suspend (isSuccess: Boolean) -> Unit)? = null,
    showLoading: Boolean = true,
    scope: CoroutineScope,
): Job {
    var isSuccess = false
    return scope.launch {
        if (showLoading) onReply?.invoke(ResponseState.Loading())
        try {
            val response = action()
            if (response.isSuccessful) {
                val body = response.body()
                if (body == null) onReply?.invoke(
                    ResponseState.Error(message = "Null body", errors = null)
                )
                else {
                    isSuccess = true
                    onReply?.invoke(ResponseState.Success(data = body, lastStep = body.lastStep))
                }
            } else {

                val errorBody = getBodyError(response)
                val errorMsg = errorBody?.message

                when (response.code()) {
                    401 -> onReply?.invoke(ResponseState.NotAuthorized())
                    500 -> onReply?.invoke(ResponseState.UnKnownError())
                    else -> {
                        onReply?.invoke(
                            ResponseState.Error(
                                message = errorMsg,
                                errors = errorBody?.errors,
                                data = response.body(),
                                lastStep = errorBody?.lastStep
                            )
                        )
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("OkHttpClient", "networkCall: ${e.cause}\n.")
            Log.e("OkHttpClient", "networkCall: ${e.localizedMessage}")
            when (e) {
                is IOException -> {
                    if (e.localizedMessage?.contains("Unable to resolve host") == true)
                        onReply?.invoke(ResponseState.NetworkError())
                    else onReply?.invoke(ResponseState.UnKnownError())
                }
                else -> onReply?.invoke(ResponseState.UnKnownError())
            }
        } finally {
            doAfter?.invoke(isSuccess)
        }

    }
}

fun <T> getBodyError(response: Response<T>): BaseResponse? {
    return try {
        val errorResponse =
            Gson().fromJson(response.errorBody()?.charStream(), BaseResponse::class.java)
        errorResponse
    } catch (exception: Exception) {
        Log.e("OkHttpClient", exception.localizedMessage ?: "Unknown Error")
        null
    }
}

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isNetworkAvailable(): Boolean {
    return try {
        val cm: ConnectivityManager =
           getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        cm.getNetworkCapabilities(cm.activeNetwork)!!.hasCapability(NET_CAPABILITY_INTERNET)
    } catch (e: java.lang.Exception) {
        false
    }
}