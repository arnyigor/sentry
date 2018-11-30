package com.arny.sentry.presenter.main

import com.arny.sentry.presenter.base.BaseMvpPresenter
import com.arny.sentry.presenter.base.BaseMvpView

object MainContract {
    interface View : BaseMvpView {
        fun showProgress(msg: String)
        fun showError(message: String?)
    }

    interface Presenter : BaseMvpPresenter<View> {
    }
}