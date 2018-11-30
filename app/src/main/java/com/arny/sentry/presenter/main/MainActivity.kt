package com.arny.sentry.presenter.main

import android.os.Bundle
import com.arny.sentry.R
import com.arny.sentry.data.utils.parseDouble
import com.arny.sentry.data.utils.parseInt
import com.arny.sentry.presenter.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_get.setOnClickListener {
            val dist = edt_dist.text.toString().parseDouble()
            val year = edt_max_year.text.toString().parseInt()
            val checked = switch_dist_type.isChecked
            mPresenter.request(dist, checked, year)
        }
    }

    override fun showProgress(msg: String) {
        tv_info.text = msg
    }

    override fun showError(message: String?) {
        tv_info.text = message
    }

}
