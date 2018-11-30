package com.arny.sentry.data.models

import com.arny.sentry.data.utils.Utility

class Asteroid {
    var name: String? = null
    var orbit_id: String? = null
    var jd: Double? = null
    var cd: String? = null
    var dist: Double? = null
    var dist_min: Double? = null
    var dist_max: Double? = null
    var v_rel: Double? = null
    var v_inf: Double? = null
    var t_sigma_f: String? = null
    var body: String? = null
    var h: Double? = null
    var fullname: String? = null
    override fun toString(): String {
        return Utility.getFields(this)
    }
}