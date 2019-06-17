package com.arny.sentry.di

import android.content.Context
import com.arny.sentry.data.source.MainRepositoryImpl
import com.arny.sentry.presenter.main.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun getContext(): Context
    fun inject(mainPresenter: MainPresenter)
}