package com.ieee.codelink.data.remote

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import com.ieee.codelink.domain.models.responses.AuthResponse
import com.ieee.codelink.domain.models.responses.CommentsResponse
import com.ieee.codelink.domain.models.responses.CreatePostResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.domain.models.responses.InvitesResponse
import com.ieee.codelink.domain.models.responses.LikesResponse
import com.ieee.codelink.domain.models.responses.NotificationsResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.domain.models.responses.ProfileUserResponse
import com.ieee.codelink.domain.models.responses.ShareResponse
import com.ieee.codelink.domain.models.responses.TeamResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiRemoteService {


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

    @Multipart
    @POST(CREATE_POST)
    suspend fun createPost(
        @Header("Authorization")
        token: String,
        @Part
        file_path: MultipartBody.Part?,
        @Part("content")
        content: RequestBody?
    ): Response<CreatePostResponse>


    @POST
    suspend fun likePost(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<BaseResponse>

    @GET
    suspend fun getPostLikes(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<LikesResponse>

    @GET
    suspend fun getPostComments(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ):Response<CommentsResponse>

    @POST
    suspend fun createComment(
        @Url url: String,
        @Header("Authorization")
        token: String,
        @Query("content")
        content: String
    ):Response<CommentsResponse>

    @POST
    suspend fun sharePost(
        @Url url: String,
        @Header("Authorization")
        token: String
    ):Response<ShareResponse>

    @GET
    suspend fun getProfileUser(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ):Response<ProfileUserResponse>

    @Multipart
    @POST
    suspend fun updateProfile(
        @Url url: String,
        @Header("Authorization")
        token: String,
        @Part
        imageUrl: MultipartBody.Part?,
        @Part("name")
        name: RequestBody?,
        @Part("track")
        track: RequestBody?,
        @Part("bio")
        bio: RequestBody?
    ): Response<AuthResponse>

    @POST
    suspend fun editUserInfo(
        @Url url: String,
        @Header("Authorization")
        token: String,
        @Query("governate")
        governate: String?,
        @Query("university")
        university: String?,
        @Query("faculty")
        faculty: String?,
        @Query("birthDate")
        birthDate: String?,
        @Query("emailProfile")
        emailProfile: String?,
        @Query("phoneNumber")
        phoneNumber: String?,
        @Query("projects")
        projects: String?,
        @Query("progLanguages")
        progLanguages: String?,
        @Query("cvUrl")
        cvUrl: String?,
        @Query("githubUrl")
        githubUrl: String?,
        @Query("linkedinUrl")
        linkedinUrl: String?,
        @Query("behanceUrl")
        behanceUrl: String?,
        @Query("twitterUrl")
        twitterUrl: String?,
        @Query("facebookUrl")
        facebookUrl: String?
    ): Response<BaseResponse>

    @GET(GET_ALL_USERS)
    suspend fun getAllUsers(
        @Header("Authorization")
        token: String
    ): Response<AllUsersResponse>
    @GET(GET_ALL_TEAMS)
    suspend fun getAllTeams(
        @Header("Authorization")
        token: String
    ): Response<AllTeamsResponse>

    @Multipart
    @POST(CREATE_TEAM)
    suspend fun createTeam(
        @Header("Authorization")
        token: String,
        @Part("name")
        name: RequestBody,
        @Part("description")
        description: RequestBody,
        @Part
        imageUrl: MultipartBody.Part?,
    ): Response<CreateTeamResponse>

    @Multipart
    @POST
    suspend fun editTeam(
        @Url url: String,
        @Header("Authorization")
        token: String,
        @Part("name")
        name: RequestBody?,
        @Part("description")
        description: RequestBody?,
        @Part
        imageUrl: MultipartBody.Part?,
    ): Response<CreateTeamResponse>

    @POST
    suspend fun deletePost(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<BaseResponse>?

    @GET(GET_USER_TEAMS)
    suspend fun getUserTeams(
        @Header("Authorization")
        token: String,
    ): Response<AllTeamsResponse>?

    @GET(GET_LEADER_TEAMS)
    suspend fun getTeamsWhereUserIsLeader(
        @Header("Authorization")
        token: String,
    ): Response<AllTeamsResponse>?

    @GET
    suspend fun getTeamById(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ):Response<TeamResponse>

    @POST
    suspend fun requestToJoinTeam(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<BaseResponse>?
    @POST
    suspend fun inviteToTeam(
        @Url url: String,
        @Header("Authorization")
        token: String,
        @Query("user_id")
        user_id: Int,
    ): Response<BaseResponse>?

    @GET(INVITE_REQUESTS)
    suspend fun getInvitations(
        @Header("Authorization")
        token: String,
    ):Response<InvitesResponse>
    @POST
    suspend fun acceptInvitation(
        @Url url : String,
        @Header("Authorization")
        token: String,
    ):Response<BaseResponse>
    @POST
    suspend fun acceptJoin(
        @Url url : String,
        @Header("Authorization")
        token: String,
    ):Response<BaseResponse>
    @POST
    suspend fun rejectInvitation(
        @Url url : String,
        @Header("Authorization")
        token: String,
    ):Response<BaseResponse>

    @POST
    suspend fun rejectJoin(
        @Url url : String,
        @Header("Authorization")
        token: String,
    ):Response<BaseResponse>

    @POST
    suspend fun leaveTeam(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<BaseResponse>

    @POST
    suspend fun deleteTeam(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<BaseResponse>
    @POST
    suspend fun deleteAccount(
        @Url url: String,
        @Header("Authorization")
        token: String,
    ): Response<BaseResponse>


    @GET
    suspend fun getUserPosts(
        @Url url: String,
        @Header("Authorization")
        token: String
    ):Response<PostsResponse>
    @GET(GET_ALL_NOTIFICATIONS)
    suspend fun getAllNotifications(
        @Header("Authorization")
        token: String
    ):Response<NotificationsResponse>
}