package com.arny.sentry.data.source

import com.arny.sentry.data.api.ApiProvider
import com.arny.sentry.data.api.CadResponse
import com.arny.sentry.data.api.SentryApi
import com.arny.sentry.data.api.SentryApiService
import kotlinx.coroutines.Deferred

interface RemoteRepository : BaseRepository {
     fun requestApi(maxDist: String, maxYear: Int): Deferred<CadResponse> {
        val map = mapOf(
            "dist-max" to maxDist,
            "date-max" to "$maxYear-01-01",
            "sort" to "dist",
            "date-min" to "2018-11-01"
        )
        return ApiProvider.provideApi(SentryApi.BASE_URL, SentryApiService::class.java,useCoroutinAdapter = true).request(map)
    }
}