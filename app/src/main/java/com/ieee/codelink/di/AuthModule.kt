package com.ieee.codelink.data.remote.di

import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import com.ieee.codelink.data.remote.ApiAuthService
import com.ieee.codelink.data.remote.AuthInterceptor
import com.ieee.codelink.data.repository.AuthRepository
import com.ieee.codelink.featureAuth.di.useCaces.CacheUserUseCace
import com.ieee.codelink.featureAuth.di.useCaces.LoginUserCase
import com.ieee.codelink.featureAuth.di.useCaces.UserCaces
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

    @Provides
    @Singleton
    fun provideAuthUseCaces(
        repository: AuthRepository,
        sharedPreferenceManger: SharedPreferenceManger
    ): UserCaces = UserCaces(
        LoginUserCase(repository),
        CacheUserUseCace(sharedPreferenceManger)
    )



    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}
