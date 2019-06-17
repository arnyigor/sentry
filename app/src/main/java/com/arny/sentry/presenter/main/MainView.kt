package com.arny.sentry.presenter.main

import com.arellomobile.mvp.MvpView
import com.arny.sentry.data.models.Asteroid

interface MainView : MvpView {
    fun showProgress(vis: Boolean)
    fun showError(message: String?)
    fun updateList(list: ArrayList<Asteroid>)
    fun setInfoVisible(vis: Boolean)
}