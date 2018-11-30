package com.arny.sentry.presenter.main

import android.util.Log
import com.arny.sentry.data.source.MainRepositoryImpl
import com.arny.sentry.data.utils.launchAsync
import com.arny.sentry.presenter.base.BaseMvpPresenterImpl
import kotlinx.coroutines.Job


class MainPresenter : BaseMvpPresenterImpl<MainContract.View>(), MainContract.Presenter {
    private val repository = MainRepositoryImpl.instance
    private var job: Job? = null

    override fun detachView() {
        super.detachView()
        job?.cancel()
    }

    fun request(distance: Double? = 1.0, isLd: Boolean, year: Int? = 2018) {
        val requestDistance: String = if (!isLd) distance.toString() else distance.toString() + "LD"
        mView?.showProgress("Загрузка данных")
        job = launchAsync({
            val cadResponse = repository.requestApi(requestDistance, year?:2018).await()
            repository.convertCadResponse(cadResponse)
        }, {
            mView?.showProgress("Данные загружены")
            Log.i(MainPresenter::class.java.simpleName, "request: $it");
        }, {
            it.printStackTrace()
            mView?.showProgress("")
            mView?.showError(it.message)
        })
    }
}