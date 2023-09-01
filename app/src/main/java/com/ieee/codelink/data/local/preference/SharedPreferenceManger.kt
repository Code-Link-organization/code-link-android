package com.ieee.codelink.data.local.preference

import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import com.google.gson.Gson
import com.ieee.codelink.domain.models.User
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferenceManger @Inject constructor(
    @ApplicationContext context: Context,
) {

    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson = Gson()

    val hasLoggedIn: Boolean
        get() = getStringValue(TOKEN).isNotEmpty()

    var isOnboardingFinished: Boolean
        get() = getBooleanValue(IS_ONBOARDING_FINISHED)
        set(value) = setValue(IS_ONBOARDING_FINISHED, value)

    var bearerToken: String
        set(value) = setValue(TOKEN, value)
        get() = "Bearer ${getStringValue(TOKEN)}"

    var language: String
        get() = getStringValue(CURRENT_LANGUAGE, defaultValue = INITIAL_LANG)
        set(value) = setValue(CURRENT_LANGUAGE, value)

    var lat: Float
        get() = getFloatValue(LAT)
        set(value) = setValue(LAT, value)

    var lng: Float
        get() = getFloatValue(LNG)
        set(value) = setValue(LNG, value)


    fun setValue(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun setValue(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun setValue(key: String, value: Float) {
        editor.putFloat(key, value)
        editor.apply()
    }

    fun setValue(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getStringValue(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun getIntegerValue(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getFloatValue(key: String): Float {
        return sharedPreferences.getFloat(key, 0F)
    }

    fun getBooleanValue(key: String , defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun cacheUser(user: User){
        val json = gson.toJson(user)
        setValue(CACHED_USER, json)
    }

    fun getCachedUser(): User?{
        val value = getStringValue(CACHED_USER)
        val innerUser = gson.fromJson(value, User::class.java)
        return innerUser ?: null
    }

    fun logout(){
        setValue(TOKEN, "")
    }

    companion object {
        // TODO replace this with your pref name for the app
        private const val SHARED_PREFERENCES_NAME = "alexon_android_base_app"
        private const val OPENED_THE_APP_BEFORE = "OPENED_THE_APP_BEFORE"
        private const val TOKEN = "TOKEN"
        private const val LAT = "LAT"
        private const val LNG = "LNG"
        const val INITIAL_LANG = "en"
        const val IS_ONBOARDING_FINISHED = "onBoarding"
        const val CACHED_USER = "user"
        const val CURRENT_LANGUAGE = "currentLanguage"

    }
}