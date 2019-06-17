package com.arny.sentry.domain

import com.arny.core.observeOnMain
import com.arny.sentry.data.models.Asteroid
import com.arny.sentry.data.source.MainRepositoryImpl
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AsteroidsUseCase @Inject constructor(private val repository: MainRepositoryImpl) {
    fun getAsteroids(distance: Double?, maxYear: Int?, name: String?, useLunarDistance: Boolean): Observable<ArrayList<Asteroid>> {
        val requestDistance: String = if (!useLunarDistance) distance.toString() else distance.toString() + "LD"
        return Observable.just(1).flatMap { repository.requestApiAsteroids(requestDistance, maxYear, name) }
                .map { repository.convertCadResponse(it) }
                .observeOnMain()
    }
}