package com.ieee.codelink.featureAuth.domain.repository

import com.ieee.codelink.featureAuth.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.featureAuth.data.remote.ApiAuthService
import com.ieee.codelink.featureAuth.domain.models.AuthResponse
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

class AuthRepository (private val api: ApiAuthService , private val sharedPreferenceManger: SharedPreferenceManger) {
    suspend fun loginUser(email: String, password: String): Response<AuthResponse>? {
        return try {
            api.loginUser(email, password)
        } catch (e: Exception) {
            null
        }
    }

}