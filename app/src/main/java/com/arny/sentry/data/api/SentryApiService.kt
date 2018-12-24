package com.arny.sentry.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface SentryApiService {
    @GET("/cad.api")
    fun request(@QueryMap params: Map<String, String>): Observable<CadResponse>
}
