package com.ieee.codelink.data.repository

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.remote.GET_USER
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.EditProfileResponse
import com.ieee.codelink.domain.models.responses.ProfileUserResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class ProfileRepository(
    private val api: ApiRemoteService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {

     fun getCachedUser(): User {
        return sharedPreferenceManger.getCachedUser()!!
    }

    fun logout() {
        sharedPreferenceManger.logout()
    }
    suspend fun editUserProfileData() : Response<EditProfileResponse>?{
//        val userToken = sharedPreferenceManger.bearerToken
//        val token = "Bearer $userToken"
//        val mediaType = "multipart/form-data".toMediaType()
//        return try {
//            api.createPost(
//                token = token,
//                content = content?.toRequestBody(mediaType) ?: " ".toRequestBody(mediaType),
//                file_path = imgPart
//            )
//        } catch (e: Exception) {
//            null
//        }
        return null
    }

    suspend fun getProfileUser(userId:Int): Response<ProfileUserResponse>?{
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val url = GET_USER+userId
        return try {
            api.getProfileUser(
                token = token,
                url = url
            )
        } catch (e: Exception) {
            null
        }
    }


    suspend fun inviteUserToTeams(){

    }

    suspend fun getUserPersonalDetails(){

    }

}