package com.arny.sentry.presenter.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.arny.sentry.R
import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.data.utils.parseDouble
import com.arny.sentry.data.utils.parseInt
import com.arny.sentry.data.utils.setVisible
import com.arny.sentry.presenter.base.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseMvpActivity<MainContract.View, MainPresenter>(), MainContract.View {
    private var adapter: MainAdapter? = null

    override fun initPresenter(): MainPresenter {
        return MainPresenter()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mPresenter.onPresenterDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initAdapter()
        swipe.setOnRefreshListener {
            requestAsteroids()
        }
    }

    override fun setInfoVisible(vis: Boolean) {
         tv_info.setVisible(vis)
    }

    override fun showProgress(vis: Boolean) {
        swipe.isRefreshing = vis
    }

    private fun initAdapter() {
        adapter = MainAdapter()
        rv_items.layoutManager = LinearLayoutManager(this)
        rv_items.adapter = adapter
    }

    private fun requestAsteroids() {
        val dist = edt_dist.text.toString().parseDouble()
        val year = edt_max_year.text.toString().parseInt()
        val useLunarDistance = check_box_lunar_distance.isChecked
        mPresenter.request(dist, useLunarDistance, year)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.restoreState()
    }

    override fun updateList(list: ArrayList<Asteroid>) {
        adapter?.addAll(list)
    }

    override fun showError(message: String?) {
        tv_info.text = message
    }

}
