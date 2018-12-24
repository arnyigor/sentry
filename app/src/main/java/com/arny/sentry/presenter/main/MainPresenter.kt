package com.arny.sentry.presenter.main

import com.arny.basemvp.data.utils.observeOnMain
import com.arny.sentry.data.api.getResponseError
import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.data.source.MainRepositoryImpl
import com.arny.sentry.presenter.base.BaseMvpPresenterImpl
import io.reactivex.disposables.CompositeDisposable


class MainPresenter : BaseMvpPresenterImpl<MainContract.View>(), MainContract.Presenter {
    private val repository = MainRepositoryImpl.instance
    private val compositeDisposable = CompositeDisposable()
    private var requesting = false
    private var cache: ArrayList<Asteroid> = arrayListOf()

    override fun restoreState() {
        if (requesting) {
            mView?.showProgress(true)
        }
        if (cache.isNotEmpty()) {
            mView?.updateList(cache)
        }
    }

    fun request(distance: Double? = 1.0, useLunarDistance: Boolean, year: Int?, name: String?) {
        if (requesting) {
            mView?.showError("Данные все еще загружаются")
            return
        }
        compositeDisposable.clear()
        val requestDistance: String = if (!useLunarDistance) distance.toString() else distance.toString() + "LD"

        mView?.showProgress(true)
        val subscribe = repository.requestApiAsteroids(requestDistance, year, name)
                .map { repository.convertCadResponse(it) }
                .observeOnMain()
                .doOnSubscribe {
                    requesting = true
                }
                .doOnDispose {
                    requesting = false
                    mView?.showProgress(false)
                }
                .subscribe({
                    mView?.showProgress(false)
                    requesting = false
                    this.cache = it
                    mView?.updateList(it)
                    if (it.isNotEmpty()) {
                        mView?.setInfoVisible(false)
                    } else {
                        mView?.setInfoVisible(true)
                        mView?.showError("нет данных")
                    }
                }, {
                    mView?.showProgress(false)
                    requesting = false
                    it.printStackTrace()
                    mView?.setInfoVisible(true)
                    mView?.showError(getResponseError(it))
                })
        compositeDisposable.add(subscribe)
    }
}