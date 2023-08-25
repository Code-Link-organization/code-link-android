package com.ieee.codelink.ui.auth.login

import android.app.Application
import com.ieee.codelink.common.parseErrorMessage
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.domain.models.AuthResponse
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val app : Application
) : BaseViewModel() {


    val loginRequestState: MutableStateFlow<ResponseState<AuthResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val checkOtpState: MutableStateFlow<ResponseState<AuthResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val sendOtpState: MutableStateFlow<ResponseState<AuthResponse>> =
        MutableStateFlow(ResponseState.Empty())

    suspend fun login(email: String, password: String) {
        loginRequestState.value = ResponseState.Loading()
        val response = authRepository.loginUser(email, password)
        loginRequestState.value = handleAuthResponse(response)
    }

    private fun handleAuthResponse(response: Response<AuthResponse>?): ResponseState<AuthResponse> {
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
        return ResponseState.Error(errorMessage, null)
    }

    suspend fun cacheUser(user: User) {
        authRepository.cacheUser(user)
    }

    suspend fun sendOtpToUserEmail(email: String) {
        sendOtpState.value = ResponseState.Loading()
        val response = authRepository.sendOtpToUserEmail(email)
        sendOtpState.value = handleAuthResponse(response)
    }

    suspend fun checkOtp(code: String, email: String) {
        checkOtpState.value = ResponseState.Loading()
        val response = authRepository.verifyCode(code,email)
        checkOtpState.value = handleAuthResponse(response)
    }



}

