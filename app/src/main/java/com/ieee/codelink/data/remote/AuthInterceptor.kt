package com.ieee.codelink.data.remote

import android.util.Log
import com.ieee.codelink.data.local.preference.SharedPreferenceManger
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sharedPreferenceManger: SharedPreferenceManger,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())

        val shouldAttachAuthHeader = invocation
            .method()
            .annotations
            .any { it.annotationClass == Authenticated::class }

        val shouldAttachLatLong = invocation
            .method()
            .annotations
            .any { it.annotationClass == LatLong::class }

        return chain.proceed(
            chain.request()
                .newBuilder()
                .apply {
                    addHeader("Accept", "application/json")
                    addHeader("Content-Type", "application/json")
                    addHeader("Accept-Language", sharedPreferenceManger.language)
                    Log.e("Accept-Language", "intercept: ${sharedPreferenceManger.language}")
                    if (shouldAttachAuthHeader) {
                        addHeader("Authorization", sharedPreferenceManger.bearerToken)
                    }

                    if (shouldAttachLatLong) {
                        addHeader("lat", sharedPreferenceManger.lat.toString())
                        addHeader("lng", sharedPreferenceManger.lng.toString())
                    }
                }
                .build()
        )
    }

}

@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated

@Target(AnnotationTarget.FUNCTION)
annotation class LatLong