package com.ieee.codelink.ui.auth.signup

import android.util.Log
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.domain.models.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {

    val signUpState: MutableStateFlow<ResponseState<AuthResponse>> =
        MutableStateFlow(ResponseState.Empty())

   suspend fun signup(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        networkCall(
            {
                authRepository.signup(name, email, password, confirmPassword)
            },
            {
                Log.d("mohamed", "signup: $it")
                signUpState.value = it
            }
        )

    }

}

