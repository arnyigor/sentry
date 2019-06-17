package com.arny.sentry

import android.app.Application
import android.content.Context
import com.arny.sentry.di.AppComponent
import com.arny.sentry.di.AppModule
import com.arny.sentry.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import io.realm.Realm
import io.realm.RealmConfiguration

class SentryApp : Application() {
    companion object {
        @JvmStatic
        lateinit var appContext: Context
        @JvmStatic
        lateinit var appComponent: AppComponent
    }
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        appComponent =  DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
        Stetho.initializeWithDefaults(this)
        Realm.init(appContext)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder().name("myrealm.realm").build())
    }
}
