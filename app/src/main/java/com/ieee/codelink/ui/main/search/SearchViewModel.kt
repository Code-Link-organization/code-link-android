package com.ieee.codelink.ui.main.search

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.AuthResponse
import com.ieee.codelink.domain.models.TempModel
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {
    var x = 0

    val searchServiceList: MutableStateFlow<ArrayList<TempModel>> =
        MutableStateFlow(ArrayList())

    val searchList: MutableStateFlow<ArrayList<TempModel>> =
        MutableStateFlow(ArrayList())

    init {
        val list = ArrayList<TempModel>()
        list.add(TempModel("Teams", R.drawable.ic_teams_img))
        list.add(TempModel("Courses", R.drawable.ic_courses_img))
        list.add(TempModel("Mentor", R.drawable.ic_mentor_img))
        list.add(TempModel("Friends", R.drawable.ic_friends_img))
        searchList.value.addAll(list)

        val list2 = ArrayList<TempModel>()
        list2.addAll(searchList.value)
        list2.addAll(searchList.value)
        list2.addAll(searchList.value)
        list2.addAll(searchList.value)
        searchServiceList.value.addAll(list2)
    }

    fun getServices():ArrayList<TempModel> = searchList.value
    fun getTracks(): ArrayList<TempModel> = searchServiceList.value
}