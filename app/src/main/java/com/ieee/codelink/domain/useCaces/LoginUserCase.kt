package com.ieee.codelink.featureAuth.di.useCaces

import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.domain.models.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class LoginUserCase (val repository: AuthRepository){
    suspend operator fun invoke(email:String , password : String) :Response<AuthResponse> = withContext(Dispatchers.IO) {
        repository.loginUser(email, password)
    }
}