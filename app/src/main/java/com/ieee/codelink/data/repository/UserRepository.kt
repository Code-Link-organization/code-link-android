package com.ieee.codelink.data.repository

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.domain.models.User

class UserRepository(
    private val api: ApiRemoteService,
    private val sharedPreferenceManger: SharedPreferenceManger
) {

     fun getCachedUser(): User {
        return sharedPreferenceManger.getCachedUser()!!
    }

    fun logout() {
        sharedPreferenceManger.logout()
    }
}