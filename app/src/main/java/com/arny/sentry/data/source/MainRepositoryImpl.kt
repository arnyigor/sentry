package com.arny.sentry.data.source

import android.content.Context
import com.arny.sentry.SentryApp
import io.realm.Realm
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MainRepositoryImpl @Inject constructor() : BaseRepository, RemoteRepository, LocalRepository, DBRepository {
    private lateinit var realm: Realm

    override fun getDB(): Realm {
        realm = Realm.getDefaultInstance()
        return realm
    }

    override fun getContext(): Context {
        return SentryApp.appContext
    }
}
