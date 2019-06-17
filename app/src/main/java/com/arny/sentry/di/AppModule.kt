package com.arny.sentry.di

import android.content.Context
import com.arny.sentry.data.source.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return context
    }
}