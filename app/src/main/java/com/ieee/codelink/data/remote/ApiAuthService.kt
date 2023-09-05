package com.ieee.codelink.data.remote

import com.ieee.codelink.domain.models.responses.AuthResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiAuthService {


    //AUTH
    @POST(LOGIN_END_POINT)
    suspend fun loginUser(
        @Query("email")
        email: String? = null,
        @Query("password")
        password: String? = null
    ): Response<AuthResponse>

    @POST(SIGNUP_END_POINT)
    suspend fun signUpUser(
        @Query("name")
        name: String? = null,
        @Query("email")
        email: String? = null,
        @Query("password")
        password: String? = null,
        @Query("password_confirmation")
        password_confirmation: String? = null
    ): Response<AuthResponse>

    @POST(SEND_VERIFICATION_CODE)
    suspend fun sendOtp(
        @Query("email")
        email: String? = null
    ): Response<AuthResponse>

    @POST(CHECK_VERIFICATION_CODE)
    suspend fun verifyOtpCode(
        @Query("email")
        email: String? = null,
        @Query("code")
        code: String? = null
    ): Response<AuthResponse>

    @POST(RESET_PASSWORD)
    suspend fun resetPassword(
        @Header("Authorization")
        token: String,
        @Query("password")
        password: String? = null,
        @Query("password_confirmation")
        passwordConfirmation: String? = null
    ): Response<AuthResponse>

    //HOME

    @GET(GET_POSTS)
    suspend fun getPosts(
        @Header("Authorization")
        token: String
    ):Response<PostsResponse>
}