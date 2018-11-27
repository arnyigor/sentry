package com.arny.sentry.presenter.main

import android.os.Bundle
import com.arny.sentry.R
import com.arny.sentry.presenter.base.BaseMvpActivity


class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter.request()
    }

}
