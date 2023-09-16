package com.ieee.codelink.data.repository

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.remote.DELETE_ACCOUNT
import com.ieee.codelink.data.remote.GET_POSTS
import com.ieee.codelink.data.remote.LIKE_A_POST
import com.ieee.codelink.data.remote.USER_POSTS
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
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

    suspend fun deleteAccount():Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$DELETE_ACCOUNT/${getCachedUser().id}"
        return try {
            api.deleteAccount(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }


    suspend fun getUserPosts(): Response<PostsResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$USER_POSTS/${getCachedUser().id}"
        return try {
            api.getUserPosts(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
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