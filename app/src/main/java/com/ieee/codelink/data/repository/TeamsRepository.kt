package com.ieee.codelink.data.repository

import android.util.Log
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class TeamsRepository(
    private val api: ApiRemoteService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {

    fun getCachedUser(): User {
        return sharedPreferenceManger.getCachedUser()!!
    }

    fun cacheUser(newUser: User) {
        sharedPreferenceManger.cacheUser(newUser)
    }

    suspend fun getAllTeams(): Response<AllTeamsResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.getAllTeams(token)
        } catch (e: Exception) {
          //  Log.d("mohamed", "getAllTeams: ${e.message}")
            null
        }
    }

    suspend fun createTeam(
        imgPart: MultipartBody.Part?,
        name: String,
        description: String
    ): Response<CreateTeamResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val mediaType = "multipart/form-data".toMediaType()
        return try {
            api.createTeam(
                token =token,
                imageUrl = imgPart,
                name = name.toRequestBody(mediaType),
                description = description.toRequestBody(mediaType)
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun editTeam(
        imgPart: MultipartBody.Part?,
        name: String?,
        description: String?
    ): Response<CreateTeamResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val mediaType = "multipart/form-data".toMediaType()
        return try {
            api.editTeam(
                token =token,
                imageUrl = imgPart,
                name = name?.toRequestBody(mediaType),
                description = description?.toRequestBody(mediaType)
            )
        } catch (e: Exception) {
            null
        }
    }


}