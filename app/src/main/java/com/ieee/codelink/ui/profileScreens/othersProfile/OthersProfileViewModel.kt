package com.ieee.codelink.ui.profileScreens.othersProfile

import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.ProfileRepository
import com.ieee.codelink.domain.models.responses.EditProfileResponse
import com.ieee.codelink.domain.models.responses.ProfileUserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class OthersProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
) : BaseViewModel() {

    val editProfileState: MutableStateFlow<ResponseState<EditProfileResponse>?> =
        MutableStateFlow(null)
    val profileUserState: MutableStateFlow<ResponseState<ProfileUserResponse>?> =
        MutableStateFlow(null)

    suspend fun editProfile(userId : Int){
        editProfileState.value = ResponseState.Loading()
        val response = profileRepository.editUserProfileData()
        editProfileState.value = handleResponse(response)
    }
    suspend fun getProfileUser(userId : Int){
        profileUserState.value = ResponseState.Loading()
        val response = profileRepository.getProfileUser(userId)
        profileUserState.value = handleResponse(response)
    }

}