package com.ieee.codelink.featureAuth.domain.repository

import com.ieee.codelink.featureAuth.data.remote.ApiAuthService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val api: ApiAuthService) {
   // suspend fun loginUser(email: String, password: String)

}