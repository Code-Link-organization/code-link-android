package com.ieee.codelink.ui.notification

import android.content.Context
import android.net.Uri
import com.ieee.codelink.common.cacheImageToFile
import com.ieee.codelink.common.createMultipartBodyPartFromFile
import com.ieee.codelink.common.getImageFileFromRealPath
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.core.isSuccess
import com.ieee.codelink.data.repository.NotificationsRepository
import com.ieee.codelink.data.repository.TeamsRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.domain.models.responses.InvitesResponse
import com.ieee.codelink.domain.models.responses.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : BaseViewModel() {

    val invitesState: MutableStateFlow<ResponseState<InvitesResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val acceptInvitationState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val rejectInvitationState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())

    suspend fun getUserInvitations(){
        invitesState.value = ResponseState.Loading()
        val response = notificationsRepository.getInvitations()
        invitesState.value = handleResponse(response)
    }

    suspend fun acceptInvitation(invitationId : Int):Boolean{
        acceptInvitationState.value = ResponseState.Loading()
        val response = notificationsRepository.acceptInvitation(invitationId)
        acceptInvitationState.value = handleResponse(response)
        return acceptInvitationState.value.isSuccess
    }
    suspend fun rejectInvitation(invitationId : Int):Boolean{
        rejectInvitationState.value = ResponseState.Loading()
        val response = notificationsRepository.rejectInvitation(invitationId)
        rejectInvitationState.value = handleResponse(response)
        return rejectInvitationState.value.isSuccess
    }

}