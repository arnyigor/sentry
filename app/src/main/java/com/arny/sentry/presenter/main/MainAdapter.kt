package com.arny.sentry.presenter.main

import android.annotation.SuppressLint
import com.arny.core.adapters.SimpleAbstractAdapter
import com.arny.sentry.R
import com.arny.sentry.data.models.Asteroid
import kotlinx.android.synthetic.main.asteroid_item_layout.view.*

class MainAdapter : SimpleAbstractAdapter<Asteroid>() {

    override fun getLayout(viewType: Int): Int {
        return R.layout.asteroid_item_layout
    }

    @SuppressLint("SetTextI18n")
    override fun bindView(item: Asteroid, viewHolder: VH) {
        viewHolder.itemView.apply {
            tv_name.text = "${item.name} Radius~${item.radius} m."
            tv_min_dist_date.text = item.cd
            tv_min_dist.text = item.smartDistance
        }
    }

    override fun getDiffCallback(): DiffCallback<Asteroid>? {
        return object : DiffCallback<Asteroid>() {
            override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
                return oldItem.dist == newItem.dist && return oldItem.cd == newItem.cd
            }
        }
    }
}