package com.ieee.codelink.ui.main.search

import android.content.Context
import android.net.Uri
import com.bumptech.glide.Glide.init
import com.ieee.codelink.R
import com.ieee.codelink.common.cacheImageToFile
import com.ieee.codelink.common.createMultipartBodyPartFromFile
import com.ieee.codelink.common.getImageFileFromRealPath
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.fakeDataProvider.FakeDataProvider
import com.ieee.codelink.data.repository.TeamsRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.AllUsersResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.domain.models.responses.PostsResponse
import com.ieee.codelink.domain.tempModels.TempSearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val teamsRepository: TeamsRepository,
) : BaseViewModel() {



    val allUsersState: MutableStateFlow<ResponseState<AllUsersResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val allTeamsState: MutableStateFlow<ResponseState<AllTeamsResponse>> =
        MutableStateFlow(ResponseState.Empty())

    var firstOption : String? = null


    fun getCachedUserId() = userRepository.getCachedUser().id

    suspend fun getAllUsers(){
        allUsersState.value = ResponseState.Loading()
        val response = userRepository.getAllUsers()
        allUsersState.value = handleResponse(response)
    }

    suspend fun getAllTeams(){
        allTeamsState.value = ResponseState.Loading()
        val response = teamsRepository.getAllTeams()
        allTeamsState.value = handleResponse(response)
    }

    suspend fun requestToJoin(teamId: Int):Boolean{
        val response = teamsRepository.requestToJoinTeam(teamId)
        return response!=null && response.isSuccessful
    }
}