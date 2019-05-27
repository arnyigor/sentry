package com.arny.sentry.data.source

import android.content.Context
import com.arny.sentry.BaseApp
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class MainRepositoryImpl : BaseRepository, RemoteRepository, LocalRepository, DBRepository {
    private lateinit var realm: Realm
    private object Holder {
        val INSTANCE = MainRepositoryImpl()
    }

    override fun getDB(): Realm {
        realm = Realm.getDefaultInstance()
        return realm
    }

    companion object {
        val instance: MainRepositoryImpl by lazy { Holder.INSTANCE }
    }

    override fun getContext(): Context {
        return BaseApp.appContext
    }

    fun getMainScoupe() = CoroutineScope(Dispatchers.Main)
    fun getIOScoupe() = CoroutineScope(Dispatchers.IO)

}
