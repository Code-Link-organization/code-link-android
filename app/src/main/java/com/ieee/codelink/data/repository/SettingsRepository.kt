package com.ieee.codelink.data.repository

import com.ieee.codelink.data.local.preference.SharedPreferenceManger

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


}