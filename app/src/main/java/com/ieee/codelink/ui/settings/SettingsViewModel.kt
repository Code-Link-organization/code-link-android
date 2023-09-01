package com.ieee.codelink.ui.settings

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.data.repository.UserRepository
import com.ieee.codelink.domain.models.AuthResponse
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
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