package com.arny.sentry.data.api

import com.arny.sentry.data.utils.Utility
import com.google.gson.annotations.SerializedName

class CadResponse {
    @SerializedName("data")
    var data: List<List<Any>>? = null
    @SerializedName("count")
    var count: Int? = null
    @SerializedName("fields")
    var fields: List<String>? = null

    override fun toString(): String {
        return Utility.getFields(this)
    }
}