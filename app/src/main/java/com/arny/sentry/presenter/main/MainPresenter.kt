package com.arny.sentry.presenter.main

import android.util.Log
import com.arny.sentry.data.source.MainRepositoryImpl
import com.arny.sentry.data.utils.Stopwatch
import com.arny.sentry.data.utils.launchAsync
import com.arny.sentry.presenter.base.BaseMvpPresenterImpl


class MainPresenter : BaseMvpPresenterImpl<MainContract.View>(), MainContract.Presenter {
    private val repository = MainRepositoryImpl.instance

    fun request() {
        val stopwatch = Stopwatch(true)
        launchAsync({
            repository.requestApi()
        }, {
            Log.i(MainPresenter::class.java.simpleName, "request: $it time; ${stopwatch.formatTime(3)}");
        }, {
            it.printStackTrace()
        })
    }
}