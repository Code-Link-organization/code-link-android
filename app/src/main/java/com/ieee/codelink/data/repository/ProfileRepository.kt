package com.ieee.codelink.data.repository

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.remote.EDIT_INFO
import com.ieee.codelink.data.remote.EDIT_PROFILE
import com.ieee.codelink.data.remote.GET_USER
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AuthResponse
import com.ieee.codelink.domain.models.responses.ProfileUserResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
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


    suspend fun updateProfile(
        userId: Int,
        imgPart: MultipartBody.Part?,
        name: String?,
        track: String?,
        bio: String?
    ): Response<AuthResponse>? {
        val url = EDIT_PROFILE + userId
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        val mediaType = "multipart/form-data".toMediaType()
        return try {
            api.updateProfile(
                url = url,
                token = token,
                name = name?.toRequestBody(mediaType) ?: " ".toRequestBody(mediaType),
                track = track?.toRequestBody(mediaType) ?: " ".toRequestBody(mediaType),
                bio = bio?.toRequestBody(mediaType) ?: " ".toRequestBody(mediaType),
                imageUrl = imgPart
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateUserInfo(
        userId: Int,
        governate: String? = null,
        university: String? = null,
        faculty: String? = null,
        birthDate: String? = null,
        emailProfile: String? = null,
        phoneNumber: String? = null,
        projects: String? = null,
        progLanguages: String? = null,
        cvUrl: String? = null,
        githubUrl: String? = null,
        linkedinUrl: String? = null,
        behanceUrl: String? = null,
        twitterUrl: String? = null,
        facebookUrl: String? = null
    ): Response<BaseResponse>? {
        val url = EDIT_INFO + userId
        val userToken = sharedPreferenceManger.bearerToken
        val token = "Bearer $userToken"
        return try {
            api.editUserInfo(
                url = url,
                token = token,
                governate = governate,
                university = university,
                faculty = faculty,
                birthDate = birthDate,
                emailProfile = emailProfile,
                phoneNumber = phoneNumber,
                projects = projects,
                progLanguages = progLanguages,
                cvUrl = cvUrl,
                githubUrl = githubUrl,
                linkedinUrl = linkedinUrl,
                behanceUrl = behanceUrl,
                twitterUrl = twitterUrl,
                facebookUrl = facebookUrl
            )
        } catch (e: Exception) {
            null
        }
    }


}