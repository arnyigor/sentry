package com.arny.sentry.data.source

import com.arny.sentry.data.api.ApiProvider
import com.arny.sentry.data.api.CadResponse
import com.arny.sentry.data.api.SentryApi
import com.arny.sentry.data.api.SentryApiService
import com.arny.sentry.data.utils.DateTimeUtils
import io.reactivex.Observable

interface RemoteRepository : BaseRepository {
    fun requestApiAsteroids(maxDist: String, maxYear: Int?, name: String?): Observable<CadResponse> {
        val dateTime = DateTimeUtils.getDateTime("yyyy-MM-dd")
        val params = hashMapOf<String, String>(
            "dist-max" to maxDist,
            "sort" to "dist",
            "date-min" to dateTime
        )
        if (maxYear != null) {
            params["date-max"] = "$maxYear-01-01"
        }
        if (name != null && !name.isBlank()) {
            params["des"] = name.replace("\\s+".toRegex(), "")
        }
        return ApiProvider.provideApi(SentryApi.BASE_URL, SentryApiService::class.java).request(params)
    }
}