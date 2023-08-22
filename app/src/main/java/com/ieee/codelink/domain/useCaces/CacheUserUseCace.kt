package com.ieee.codelink.featureAuth.di.useCaces

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.domain.models.AuthResponse
import com.ieee.codelink.domain.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class CacheUserUseCace (val sharedPreferenceManger: SharedPreferenceManger){
    suspend operator fun invoke(user : User)= withContext(Dispatchers.IO) {
        sharedPreferenceManger.cacheUser(user)
    }
}