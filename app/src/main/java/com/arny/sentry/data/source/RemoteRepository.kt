package com.arny.sentry.data.source

import com.arny.sentry.data.api.ApiProvider
import com.arny.sentry.data.api.CadResponse
import com.arny.sentry.data.api.SentryApi
import com.arny.sentry.data.api.SentryApiService
import com.arny.sentry.data.utils.DateTimeUtils
import io.reactivex.Observable

interface RemoteRepository : BaseRepository {
     fun requestApiAsteroids(maxDist: String, maxYear: Int): Observable<CadResponse> {
         val dateTime = DateTimeUtils.getDateTime("yyyy-MM-dd")
        val map = mapOf(
            "dist-max" to maxDist,
            "date-max" to "$maxYear-01-01",
            "sort" to "dist",
            "date-min" to dateTime
        )
        return ApiProvider.provideApi(SentryApi.BASE_URL, SentryApiService::class.java).request(map)
    }
}