package com.arny.sentry

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import io.realm.Realm
import io.realm.RealmConfiguration

class BaseApp : Application() {
    companion object {
        @JvmStatic
        lateinit var appContext: Context
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Stetho.initializeWithDefaults(this)
        Realm.init(appContext)
        val config = RealmConfiguration.Builder().name("myrealm.realm").build()
        Realm.setDefaultConfiguration(config)
    }
}
