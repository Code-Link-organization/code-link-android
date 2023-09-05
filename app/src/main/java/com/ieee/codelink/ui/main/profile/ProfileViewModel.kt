package com.ieee.codelink.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : BaseViewModel() {

    val user: MutableStateFlow<User?> =
        MutableStateFlow(null)

    init {
        viewModelScope.launch {
            user.value =getCachedUser()
        }
    }
    suspend fun getCachedUser() = userRepository.getCachedUser()
     fun logout() {
        userRepository.logout()
    }
}