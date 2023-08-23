package com.ieee.codelink.featureAuth.auth.login

import com.ieee.codelink.common.parseErrorMessage
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.domain.models.AuthResponse
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.featureAuth.di.useCaces.UserCaces
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val useCaces: UserCaces
) : BaseViewModel() {

    val loginState: MutableStateFlow<ResponseState<AuthResponse>> =
        MutableStateFlow(ResponseState.Empty())

    suspend fun login(email: String, password: String) {
        networkCall(
            {
                useCaces.login(email, password)
            },
            {
                loginState.value = it
            }
        )
    }

    suspend fun cacheUser(user: User) {
        useCaces.cacheUser(user)
    }

}

