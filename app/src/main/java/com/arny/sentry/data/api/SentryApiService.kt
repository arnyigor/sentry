package com.arny.sentry.data.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface SentryApiService {
    @GET("/cad.api?dist-max=0.1LD&date-min=2018-01-01&date-max=2030-01-01&sort=dist")
    suspend fun request(): Deferred<CadResponse>
}
