package com.ieee.codelink.data.repository

import android.util.Log
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ACCEPT_TEAM_INVITATION
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.remote.GET_TEAM
import com.ieee.codelink.data.remote.INVITE_REQUESTS
import com.ieee.codelink.data.remote.INVITE_TO_TEAM
import com.ieee.codelink.data.remote.JOIN_TEAM
import com.ieee.codelink.data.remote.REJECT_TEAM_INVITATION
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.domain.models.responses.InvitesResponse
import com.ieee.codelink.domain.models.responses.TeamResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class NotificationsRepository(
    private val api: ApiRemoteService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {

    fun getCachedUser(): User {
        return sharedPreferenceManger.getCachedUser()!!
    }

    fun cacheUser(newUser: User) {
        sharedPreferenceManger.cacheUser(newUser)
    }

    suspend fun getInvitations():Response<InvitesResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.getInvitations(
                token
            )
        } catch (e: Exception) {
            null
        }
    }


    suspend fun acceptInvitation(invitationId : Int):Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$ACCEPT_TEAM_INVITATION/$invitationId"
        return try {
            api.acceptInvitation(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }
    suspend fun rejectInvitation(invitationId : Int):Response<BaseResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = "$REJECT_TEAM_INVITATION/$invitationId"
        return try {
            api.rejectInvitation(
                url,
                token
            )
        } catch (e: Exception) {
            null
        }
    }




}