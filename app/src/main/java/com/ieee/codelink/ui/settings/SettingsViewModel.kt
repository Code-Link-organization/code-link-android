package com.ieee.codelink.ui.settings

import android.content.Context
import com.ieee.codelink.R
import com.ieee.codelink.core.BaseResponse
import com.ieee.codelink.core.BaseViewModel
import com.ieee.codelink.core.ResponseState
import com.ieee.codelink.data.repository.SettingsRepository
import com.ieee.codelink.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val context :Context,
    private val settingsRepository: SettingsRepository,
    private val userRepository: UserRepository
) : BaseViewModel() {


    val dialogLanguageFlag: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    val languageRadioVisible: MutableStateFlow<Boolean> =
        MutableStateFlow(false)

    val deleteAccountState: MutableStateFlow<ResponseState<BaseResponse>> =
        MutableStateFlow(ResponseState.Empty())


    fun logout() {
        userRepository.logout()
    }


    fun setLanguageFlagValue(boolean: Boolean) {
        dialogLanguageFlag.value = boolean
    }

    fun getCurrentLanguage(): String = settingsRepository.getCurrentLanguage()
    private fun setCurrentLanguage(lang : String)  = settingsRepository.setCurrentLanguage(lang)

    fun toggleLanguageRadioVisibility() {
        languageRadioVisible.value = !languageRadioVisible.value
    }

     fun changeLanguage(checkedLanguage: String) {
       if (checkedLanguage == context.getString(R.string.Arabic)){
           setCurrentLanguage("Arabic")
       }else{
           setCurrentLanguage("English")
       }
    }

    fun getCurrentLanguageAsSystem(): String {
       val language = getCurrentLanguage()
        return if (language == "Arabic") context.getString(R.string.Arabic)
        else context.getString(R.string.English)
    }

    fun isDarkMode(): Boolean = settingsRepository.getDarkMode()
    fun isNotificationsEnabled(): Boolean = settingsRepository.isNotificationsEnabled()
    suspend fun toggleDarkMode() = settingsRepository.toggleDarkMode()

    fun toggleNotifocationsEnabled() : Boolean = settingsRepository.toggleNotifications()

    suspend fun deleteAccount(){
        deleteAccountState.value = ResponseState.Loading()
        val response = userRepository.deleteAccount()
        deleteAccountState.value = handleResponse(response)
    }
}