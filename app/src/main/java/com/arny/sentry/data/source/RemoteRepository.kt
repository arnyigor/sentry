package com.arny.sentry.data.source

import android.util.Log
import com.arny.sentry.data.api.ApiProvider
import com.arny.sentry.data.api.CadResponse
import com.arny.sentry.data.api.SentryApi
import com.arny.sentry.data.api.SentryApiService

interface RemoteRepository : BaseRepository {
    suspend fun requestApi(): CadResponse {
        val map = mapOf(
            "des" to "2018 UA".replace(" ", "%20"),
            "dist-max" to "1LD",
            "date-max" to "2030-01-01",
            "sort" to "dist",
            "date-min" to "2018-01-01"
        )
        return ApiProvider.provideApi(SentryApi.BASE_URL, SentryApiService::class.java).request().await()
    }
}