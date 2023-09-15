package com.ieee.codelink.ui.teamsScreens

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.ieee.codelink.common.cacheImageToFile
import com.ieee.codelink.common.createMultipartBodyPartFromFile
import com.ieee.codelink.common.getImageFileFromRealPath
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.TeamsRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val teamsRepository: TeamsRepository,
    private val context: Context
) : BaseViewModel() {

    var createTeamImgUri : Uri? = null
    var editTeamImgUri : Uri? = null

    val userTeamsState: MutableStateFlow<ResponseState<AllTeamsResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val createTeamState: MutableStateFlow<ResponseState<CreateTeamResponse>> =
        MutableStateFlow(ResponseState.Empty())


    suspend fun getUserTeams(){
        userTeamsState.value = ResponseState.Loading()
        val response = teamsRepository.getUserTeams()
        userTeamsState.value = handleResponse(response)
    }

    suspend fun createTeam(name: String , description: String , uri: Uri?){
        val imgPart = if (uri !=null){
            val path = cacheImageToFile( context , uri)
            val file = getImageFileFromRealPath(path)
            createMultipartBodyPartFromFile(file , "imageUrl")
        } else{
            null
        }
        createTeamState.value = ResponseState.Loading()
        val response = teamsRepository.createTeam(
            name= name,
            description= description,
            imgPart = imgPart
        )
        createTeamState.value = handleResponse(response)
    }

}