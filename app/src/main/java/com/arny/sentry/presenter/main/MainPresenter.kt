package com.arny.sentry.presenter.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arny.core.addTo
import com.arny.sentry.SentryApp
import com.arny.sentry.data.api.getResponseError
import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.domain.AsteroidsUseCase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var asteroidsUseCase: AsteroidsUseCase
    private val compositeDisposable = CompositeDisposable()
    private var requesting = false
    private var cache: ArrayList<Asteroid> = arrayListOf()

    init {
        SentryApp.appComponent.inject(this)
    }

    fun restoreState() {
        if (requesting) {
            viewState?.showProgress(true)
        }
        if (cache.isNotEmpty()) {
            viewState?.updateList(cache)
        }
    }

    fun request(distance: Double? = 1.0, useLunarDistance: Boolean, year: Int?, name: String?) {
        if (requesting) {
            viewState?.showError("Данные все еще загружаются")
            return
        }
        compositeDisposable.clear()
        requesting = true
        viewState?.showProgress(true)
        asteroidsUseCase.getAsteroids(distance, year, name, useLunarDistance)
                .subscribe({
                    viewState?.showProgress(false)
                    requesting = false
                    this.cache = it
                    viewState?.updateList(it)
                    if (it.isNotEmpty()) {
                        viewState?.setInfoVisible(false)
                    } else {
                        viewState?.setInfoVisible(true)
                        viewState?.showError("нет данных")
                    }
                }, {
                    requesting = false
                    viewState?.showProgress(false)
                    requesting = false
                    it.printStackTrace()
                    viewState?.setInfoVisible(true)
                    viewState?.showError(getResponseError(it))
                })
                .addTo(compositeDisposable)
    }
}