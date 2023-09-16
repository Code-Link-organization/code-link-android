package com.ieee.codelink.ui.notification

import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.core.isSuccess
import com.ieee.codelink.data.repository.NotificationsRepository
import com.ieee.codelink.domain.models.responses.InvitesResponse
import com.ieee.codelink.domain.models.responses.NotificationsResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : BaseViewModel() {

    val notificationsState: MutableStateFlow<ResponseState<NotificationsResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val acceptInvitationState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val rejectInvitationState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())

    suspend fun getUserNotificationss(){
        notificationsState.value = ResponseState.Loading()
        val response = notificationsRepository.getAllNotifications()
        notificationsState.value = handleResponse(response)
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