package com.ieee.codelink.di

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiAuthService
import com.ieee.codelink.data.repository.PostsRepository
import com.ieee.codelink.data.repository.SettingsRepository
import com.ieee.codelink.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PostsModule {
    @Provides
    @Singleton
    fun providePostsRepository(
        api: ApiAuthService,
        sharedPreferenceManger: SharedPreferenceManger
    ): PostsRepository = PostsRepository(api, sharedPreferenceManger )

}