package com.arny.sentry.data.source

import android.content.Context
import com.arny.sentry.BaseApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainRepositoryImpl : BaseRepository, RemoteRepository {
    private object Holder {
        val INSTANCE = MainRepositoryImpl()
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