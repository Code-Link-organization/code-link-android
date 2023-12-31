package com.ieee.codelink.data.repository

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.remote.DELETE_TEAM
import com.ieee.codelink.data.remote.EDIT_TEAM
import com.ieee.codelink.data.remote.GET_TEAM
import com.ieee.codelink.data.remote.INVITE_TO_TEAM
import com.ieee.codelink.data.remote.JOIN_TEAM
import com.ieee.codelink.data.remote.LEAVE_TEAM
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.domain.models.responses.TeamResponse
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
        teamId: Int,
        imgPart: MultipartBody.Part?,
        name: String?,
        description: String?
    ): Response<CreateTeamResponse>? {
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val mediaType = "multipart/form-data".toMediaType()
        val url = "$EDIT_TEAM/$teamId"
        return try {
            api.editTeam(
                url = url,
                token =token,
                imageUrl = imgPart,
                name = name?.toRequestBody(mediaType),
                description = description?.toRequestBody(mediaType)
            )
        } catch (e: Exception) {
            null
        }
    }


    suspend fun getUserTeams():Response<AllTeamsResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.getUserTeams( token)
        } catch (e: Exception) {
            null
        }
    }
    suspend fun getTeamsWhereUserIsLeader():Response<AllTeamsResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.getTeamsWhereUserIsLeader(token)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getTeamById(teamId: Int): Response<TeamResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$GET_TEAM/$teamId"
        return try {
            api.getTeamById(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun requestToJoinTeam(teamId: Int):Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$JOIN_TEAM/$teamId"
        return try {
            api.requestToJoinTeam(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }
    suspend fun inviteToTeam(teamId: Int,userId : Int):Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$INVITE_TO_TEAM/$teamId"
        return try {
            api.inviteToTeam(
                url,
                token,
                userId
            )
        } catch (e: Exception) {
            null
        }
    }


    suspend fun leaveTeam(teamId: Int):Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$LEAVE_TEAM$teamId"
        return try {
            api.leaveTeam(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }
    suspend fun deleteTeam(teamId: Int):Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$DELETE_TEAM$teamId"
        return try {
            api.deleteTeam(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }

}