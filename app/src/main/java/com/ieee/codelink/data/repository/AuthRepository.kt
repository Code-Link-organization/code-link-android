package com.ieee.codelink.data.repository

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.domain.models.responses.AuthResponse
import com.ieee.codelink.domain.models.User
import retrofit2.Response

class AuthRepository(
    private val api: ApiRemoteService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {
    suspend fun loginUser(email: String, password: String): Response<AuthResponse>? {
        return try {
            api.loginUser(email, password)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signup(
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) :Response<AuthResponse> = api.signUpUser(name, email, password, confirmPassword)

     fun cacheUser(user: User) {
        sharedPreferenceManger.cacheUser(user)
    }

    suspend fun sendOtpToUserEmail(email: String): Response<AuthResponse>? {
        return try {
            api.sendOtp(email)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun verifyCode(code: String, email: String): Response<AuthResponse>? {
        return try {
            api.verifyOtpCode(code = code,email = email)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun resetPassword(token: String, password: String): Response<AuthResponse>? {
        return try {
            api.resetPassword(token, password , password)
        } catch (e: Exception) {
            null
        }
    }

    fun getCurrentLanguage(): String {
        return sharedPreferenceManger.getStringValue(
            SharedPreferenceManger.CURRENT_LANGUAGE,
            "English"
        )
    }

    fun setOnBoardingFinished(onBoardingFinished: Boolean) {
        sharedPreferenceManger.setValue(SharedPreferenceManger.IS_ONBOARDING_FINISHED, onBoardingFinished)
    }


}