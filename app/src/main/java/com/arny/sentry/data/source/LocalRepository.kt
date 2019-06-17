package com.arny.sentry.data.source

import com.arny.core.parseDouble
import com.arny.sentry.data.api.CadResponse
import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.data.spaceutils.AstroConst
import com.arny.sentry.data.spaceutils.AstroUtils
import com.arny.sentry.data.utils.MathUtils

interface LocalRepository : BaseRepository {
    fun convertCadResponse(cadResponse: CadResponse): ArrayList<Asteroid> {
        val fields = cadResponse.fields ?: listOf()
        val data = cadResponse.data ?: listOf()
        val asteroids = arrayListOf<Asteroid>()
        for (itemdata in data) {
            val params = hashMapOf<String, Any>()
            val asteroid = Asteroid()
            for (value in fields.withIndex()) {
                val index = value.index
                val fld = value.value
                val fldValue = itemdata[index]
                params[fld] = fldValue
                convertField(fld, fldValue, asteroid)
            }
            val dist_min = asteroid.dist_min ?: 0.0
            val distanceInMetre = AstroUtils.distanceConvert(dist_min, AstroUtils.DistanceTypes.AU, AstroUtils.DistanceTypes.metre)
            val lunarDistance = MathUtils.round(distanceInMetre / AstroConst.LD, 4)
            val smartDistance = AstroUtils.getSmartDistance(dist_min * AstroConst.AU)
            asteroid.smartDistance = "$smartDistance, $lunarDistance LD"
            asteroid.radius = asteroid.h?.let { AstroUtils.getRadiusFromAbsoluteMagn(it, 0.15).toString() }
            asteroids.add(asteroid)
        }
        asteroids.sortBy { it.dist_min }
        return asteroids
    }

    fun convertField(field: String?, fieldValue: Any?, asteroid: Asteroid) {
        when (field) {
            "des" -> asteroid.name = fieldValue.toString()
            "orbit_id" -> asteroid.orbit_id = fieldValue.toString()
            "jd" -> asteroid.jd = fieldValue.toString().parseDouble()
            "cd" -> asteroid.cd = fieldValue.toString()
            "dist" -> asteroid.dist = fieldValue.toString().parseDouble()
            "dist_min" -> asteroid.dist_min = fieldValue.toString().parseDouble()
            "dist_max" -> asteroid.dist_max = fieldValue.toString().parseDouble()
            "v_rel" -> asteroid.v_rel = fieldValue.toString().parseDouble()
            "v_inf" -> asteroid.v_inf = fieldValue.toString().parseDouble()
            "t_sigma_f" -> asteroid.t_sigma_f = fieldValue.toString()
            "body" -> asteroid.body = fieldValue.toString()
            "h" -> asteroid.h = fieldValue.toString().parseDouble()
            "fullname" -> asteroid.fullname = fieldValue.toString()
        }
    }
}