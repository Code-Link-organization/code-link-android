package com.ieee.codelink.di

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiRemoteService
import com.ieee.codelink.data.repository.ProfileRepository
import com.ieee.codelink.data.repository.SettingsRepository
import com.ieee.codelink.data.repository.TeamsRepository
import com.ieee.codelink.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchModule {
    @Provides
    @Singleton
    fun provideUserRepository(
        api: ApiRemoteService,
        sharedPreferenceManger: SharedPreferenceManger
    ): TeamsRepository = TeamsRepository(api, sharedPreferenceManger )

}