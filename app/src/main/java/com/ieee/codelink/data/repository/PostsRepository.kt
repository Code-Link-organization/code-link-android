package com.ieee.codelink.data.repository

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiAuthService
import com.ieee.codelink.data.remote.GET_LIKED_USERS
import com.ieee.codelink.data.remote.GET_POSTS
import com.ieee.codelink.data.remote.LIKE_A_POST
import com.ieee.codelink.domain.models.responses.LikesResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
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


    suspend fun createPost(content: String?, imgPart: MultipartBody.Part?): Response<BaseResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val mediaType = "multipart/form-data".toMediaType()
        return try {
            api.createPost(
                token = token,
                content = content?.toRequestBody(mediaType) ?: " ".toRequestBody(mediaType),
                file_path = imgPart
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun likePost(postId : Int): Response<BaseResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$GET_POSTS/$postId/$LIKE_A_POST"
        return try {
            api.likePost(url, token)
        } catch (e: Exception) {
            null
        }
    }


    suspend fun getPostLikes(postId : Int): Response<LikesResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$GET_POSTS/$postId/$GET_LIKED_USERS"
        return try {
            api.getPostLikes(url, token)
        } catch (e: Exception) {
            null
        }
    }


}