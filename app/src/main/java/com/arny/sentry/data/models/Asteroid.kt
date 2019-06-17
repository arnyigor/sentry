package com.arny.sentry.data.models

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey


open class Asteroid: RealmObject() {
    @PrimaryKey
    var id: Long = 0
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
    @Ignore
    var smartDistance: String? = null
    @Ignore
    var radius: String? = null
    override fun toString(): String {
        return "Asteroid(id=$id, name=$name, orbit_id=$orbit_id, jd=$jd, cd=$cd, dist=$dist, dist_min=$dist_min, dist_max=$dist_max, v_rel=$v_rel, v_inf=$v_inf, t_sigma_f=$t_sigma_f, body=$body, h=$h, fullname=$fullname)"
    }


}