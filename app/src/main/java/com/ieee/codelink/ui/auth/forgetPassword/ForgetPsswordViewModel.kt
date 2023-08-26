package com.ieee.codelink.ui.auth.forgetPassword

import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.domain.models.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgetPasswordViewModel @Inject constructor(private val authRepository: AuthRepository) :
    BaseViewModel() {

    val sendOtpState: MutableStateFlow<ResponseState<AuthResponse>> =
        MutableStateFlow(ResponseState.Empty())
    suspend fun sendOtpToUserEmail(email: String) {
        sendOtpState.value = ResponseState.Loading()
        val response = authRepository.sendOtpToUserEmail(email)
        sendOtpState.value = handleResponse(response)
    }

    suspend fun resetPassword(email: String, newPassword: String) {
        authRepository.resetPassword(email, newPassword)
    }
}