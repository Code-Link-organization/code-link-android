package com.ieee.codelink.featureAuth.auth.signup

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : BaseViewModel() {
    val signUpState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())

    fun signup(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        networkCall(
            { authRepository.signup(name, email, password, confirmPassword) }, {
                signUpState.value = it
            }
        )
    }

}

