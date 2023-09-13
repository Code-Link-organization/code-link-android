package com.ieee.codelink.core

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : LocaleAwareApplication() {
    companion object {
        lateinit var context: Context
    }



    override fun onCreate() {
        super.onCreate()
        context = applicationContext

    }
}