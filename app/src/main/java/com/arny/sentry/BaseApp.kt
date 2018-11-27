package com.arny.sentry

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class BaseApp : Application() {
    companion object {
        @JvmStatic
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Stetho.initializeWithDefaults(this)
    }
}
