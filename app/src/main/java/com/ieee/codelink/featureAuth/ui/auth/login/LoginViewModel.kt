package com.ieee.codelink.featureAuth.ui.auth.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ieee.codelink.common.parseErrorMessage
import com.ieee.codelink.common.showToast
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.featureAuth.domain.models.AuthResponse
import com.ieee.codelink.featureAuth.domain.models.User
import com.ieee.codelink.featureAuth.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val app : Application
) : BaseViewModel() {

    private val _loginRequestState: MutableLiveData<ResponseState<AuthResponse>?> = MutableLiveData(null)
    val loginRequestState: LiveData<ResponseState<AuthResponse>?> = _loginRequestState
    suspend fun login(email: String, password : String){
        _loginRequestState.postValue(ResponseState.Loading())
        val response = authRepository.loginUser(email, password)
        _loginRequestState.postValue(handleLoginResponse(response))
    }

    private fun handleLoginResponse(response: Response<AuthResponse>?): ResponseState<AuthResponse>? {
        if (response?.isSuccessful == true) {
            response.body()?.let { result ->
                return ResponseState.Success(result)
            }
        }
        if (response == null) {
            return ResponseState.Error("Network error",null)
        }
        val errorBody = response.errorBody()?.string()
        val errorMessage = parseErrorMessage(errorBody)
        return ResponseState.Error(errorMessage,null)
    }

    suspend fun cacheUser(user: User) {
      authRepository.cacheUser(user)
    }

}

