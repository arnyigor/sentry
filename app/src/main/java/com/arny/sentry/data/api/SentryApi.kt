package com.arny.sentry.data.api

import com.arny.sentry.BuildConfig
import com.arny.sentry.data.source.Constants
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class SentryApi {
    private val service: ApiService

    init {
        val httpClient = OkHttpClient.Builder()
        val gson = GsonBuilder().setLenient().create()
        httpClient.connectTimeout(120, TimeUnit.SECONDS)
        httpClient.readTimeout(120, TimeUnit.SECONDS)
        httpClient.writeTimeout(120, TimeUnit.SECONDS)
        httpClient.followRedirects(true)
        httpClient.protocols(Collections.singletonList(Protocol.HTTP_1_1))
        httpClient.addInterceptor(
                HttpLoggingInterceptor()
                        .apply {
                            level =
                                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.HEADERS else HttpLoggingInterceptor.Level.NONE
                        })
        if (BuildConfig.DEBUG) {
            httpClient.networkInterceptors().add(StethoInterceptor())
        }
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.API.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        service = retrofit.create(ApiService::class.java)
    }


}
