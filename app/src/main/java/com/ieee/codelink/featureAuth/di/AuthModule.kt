package com.ieee.codelink.data.remote.di

import com.ieee.codelink.featureAuth.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.featureAuth.data.remote.ApiAuthService
import com.ieee.codelink.featureAuth.data.remote.AuthInterceptor
import com.ieee.codelink.featureAuth.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: ApiAuthService,
        sharedPreferenceManger: SharedPreferenceManger
    ): AuthRepository = AuthRepository(api, sharedPreferenceManger )

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}
