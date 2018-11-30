package com.arny.sentry.presenter.main

import com.arny.sentry.R
import com.arny.sentry.data.adapters.SimpleAbstractAdapter
import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.data.spaceutils.AstroConst
import com.arny.sentry.data.spaceutils.AstroUtils
import kotlinx.android.synthetic.main.asteroid_item_layout.view.*

class MainAdapter : SimpleAbstractAdapter<Asteroid>() {
    override fun getLayout(): Int {
        return R.layout.asteroid_item_layout
    }

    override fun bindView(item: Asteroid, viewHolder: VH) {
        viewHolder.itemView.apply {
            tv_name.text = item.name
            tv_min_dist_date.text = item.cd
            val dist_min = item.dist_min ?: 0.0
            val smartDistance = AstroUtils.getSmartDistance(dist_min * AstroConst.AU)
            tv_min_dist.text = smartDistance
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