package com.ieee.codelink.data.repository

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SettingsRepository(
    private val sharedPreferenceManger: SharedPreferenceManger
) {
    fun getCurrentLanguage(): String {
        return sharedPreferenceManger.getStringValue(
            SharedPreferenceManger.CURRENT_LANGUAGE,
            "English"
        )
    }

    fun setCurrentLanguage(lang: String) {
        sharedPreferenceManger.setValue(
            SharedPreferenceManger.CURRENT_LANGUAGE,
            lang
        )
    }

    fun getDarkMode(): Boolean {
     return sharedPreferenceManger.getBooleanValue(
         SharedPreferenceManger.DARK_MODE,
         false
     )
    }

    fun isNotificationsEnabled(): Boolean {
        return sharedPreferenceManger.getBooleanValue(
            SharedPreferenceManger.NOTIFICATIONS,
            true
        )
    }

    suspend fun toggleDarkMode() :Boolean = withContext(Dispatchers.IO) {
        val newState =  getDarkMode().not()
        sharedPreferenceManger.setValue(
            SharedPreferenceManger.DARK_MODE,
            newState
        )
        newState
    }
    fun toggleNotifications() :Boolean {
        val newState =  isNotificationsEnabled().not()
        sharedPreferenceManger.setValue(
            SharedPreferenceManger.NOTIFICATIONS,
            newState
        )
        return newState
    }


}