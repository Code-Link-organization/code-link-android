package com.ieee.codelink.di

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiAuthService
import com.ieee.codelink.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        api: ApiAuthService,
        sharedPreferenceManger: SharedPreferenceManger
    ): UserRepository = UserRepository(api, sharedPreferenceManger )
}