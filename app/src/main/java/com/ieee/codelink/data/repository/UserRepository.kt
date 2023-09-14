package com.ieee.codelink.data.repository

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.remote.GET_POSTS
import com.ieee.codelink.data.remote.LIKE_A_POST
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import retrofit2.Response

class UserRepository(
    private val api: ApiRemoteService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {

     fun getCachedUser(): User {
        return sharedPreferenceManger.getCachedUser()!!
    }

    fun logout() {
        sharedPreferenceManger.logout()
    }

    fun cacheUser(newUser: User) {
       sharedPreferenceManger.cacheUser(newUser)
    }

    suspend fun getAllUsers():Response<AllUsersResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.getAllUsers( token)
        } catch (e: Exception) {
            null
        }
    }
}