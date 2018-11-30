package com.arny.sentry.data.api

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SentryApiService {
    @GET("/cad.api")
    fun request(@QueryMap options: Map<String, String>): Deferred<CadResponse>
}
