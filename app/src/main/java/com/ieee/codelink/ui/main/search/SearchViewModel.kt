package com.ieee.codelink.ui.main.search

import com.ieee.codelink.R
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.TempSearchItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {
    var x = 0

    val searchServiceList: MutableStateFlow<ArrayList<TempSearchItem>> =
        MutableStateFlow(ArrayList())

    val searchList: MutableStateFlow<ArrayList<TempSearchItem>> =
        MutableStateFlow(ArrayList())

    init {
        val list = ArrayList<TempSearchItem>()
        list.add(TempSearchItem("Teams", R.drawable.ic_teams_img))
        list.add(TempSearchItem("Courses", R.drawable.ic_courses_img))
        list.add(TempSearchItem("Mentor", R.drawable.ic_mentor_img))
        list.add(TempSearchItem("Friends", R.drawable.ic_friends_img))
        searchList.value.addAll(list)

        val list2 = ArrayList<TempSearchItem>()
        list2.addAll(searchList.value)
        list2.addAll(searchList.value)
        list2.addAll(searchList.value)
        list2.addAll(searchList.value)
        searchServiceList.value.addAll(list2)
    }

    fun getServices():ArrayList<TempSearchItem> = searchList.value
    fun getTracks(): ArrayList<TempSearchItem> = searchServiceList.value
}