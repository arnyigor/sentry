package com.arny.sentry.presenter.main

import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.presenter.base.BaseMvpPresenter
import com.arny.sentry.presenter.base.BaseMvpView

object MainContract {
    interface View : BaseMvpView {
        fun showProgress(vis: Boolean)
        fun showError(message: String?)
        fun updateList(list: ArrayList<Asteroid>)
        fun setInfoVisible(vis: Boolean)
    }

    interface Presenter : BaseMvpPresenter<View> {
        fun restoreState()
    }
}