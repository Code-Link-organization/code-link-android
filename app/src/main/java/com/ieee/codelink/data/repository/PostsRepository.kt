package com.ieee.codelink.data.repository

import com.ieee.codelink.R
import com.ieee.codelink.common.createRandomString
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiAuthService
import com.ieee.codelink.domain.models.responses.PostsResponse
import retrofit2.Response

class PostsRepository(
    private val api: ApiAuthService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {
    suspend fun getPosts(): Response<PostsResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.getPosts(token)
        } catch (e: Exception) {
            null

        }
    }
}