package com.ieee.codelink.ui.teamsScreens

import android.content.Context
import android.net.Uri
import com.ieee.codelink.common.cacheImageToFile
import com.ieee.codelink.common.createMultipartBodyPartFromFile
import com.ieee.codelink.common.getImageFileFromRealPath
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.TeamsRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.Team
import com.ieee.codelink.domain.models.User
import com.ieee.codelink.domain.models.responses.AllTeamsResponse
import com.ieee.codelink.domain.models.responses.CreateTeamResponse
import com.ieee.codelink.domain.models.responses.TeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val teamsRepository: TeamsRepository,
    private val userRepository: UserRepository,
    private val context: Context
) : BaseViewModel() {

    var team: Team? = null
    var imgUri: Uri? = null


    val joinTeamState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())


    val userTeamsState: MutableStateFlow<ResponseState<AllTeamsResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val createTeamState: MutableStateFlow<ResponseState<CreateTeamResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val editTeamState: MutableStateFlow<ResponseState<CreateTeamResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val teamState: MutableStateFlow<ResponseState<TeamResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val leaveTeamState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())

    val deleteTeamState: MutableStateFlow<ResponseState<BaseResponse>> =
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
    suspend fun editTeam(teamId:Int , name: String? , description: String? , uri: Uri?){
        val imgPart = if (uri !=null){
            val path = cacheImageToFile( context , uri)
            val file = getImageFileFromRealPath(path)
            createMultipartBodyPartFromFile(file , "imageUrl")
        } else{
            null
        }
        editTeamState.value = ResponseState.Loading()
        val response = teamsRepository.editTeam(
            name= name,
            description= description,
            imgPart = imgPart,
            teamId = teamId
        )
        editTeamState.value = handleResponse(response)
    }

    suspend fun getTeamById(id: Int) {
        teamState.value = ResponseState.Loading()
        val response = teamsRepository.getTeamById(id)
        teamState.value = handleResponse(response)
    }

   fun isTeamLeader(team: Team,user: User = userRepository.getCachedUser() ): Boolean = user.id == team.leader_id

    fun isTeamMember(team: Team, user: User = userRepository.getCachedUser()): Boolean {
        return team.members.any { it.id == user.id }
    }

    suspend fun requestToJoinTeam(team: Team) {
        joinTeamState.value = ResponseState.Loading()
        val response = teamsRepository.requestToJoinTeam(team.id)
        joinTeamState.value = handleResponse(response)
    }

    fun leaveTeam(team: Team){
        networkCall({teamsRepository.leaveTeam(team.id)!!},{
            leaveTeamState.value=it
        })
    }
    suspend fun deleteTeam(team: Team){
        networkCall({teamsRepository.deleteTeam(team.id)!!},{
            deleteTeamState.value=it
        })
    }

    fun canUpdateTeam(name: String, description: String, team: Team): Boolean =
        name != team.name || description != team.description || imgUri != null

}