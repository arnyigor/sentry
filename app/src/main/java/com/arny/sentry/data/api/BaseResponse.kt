package com.arny.sentry.data.api

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("code")
    var code: String? = null
    @SerializedName("message")
    var message: String? = null
}