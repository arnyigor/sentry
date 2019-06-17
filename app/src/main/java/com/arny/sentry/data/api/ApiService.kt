package com.arny.sentry.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {
    @GET("/cad.api")
    fun getAsteroids(@QueryMap params: Map<String, String>): Observable<CadResponse>
}
