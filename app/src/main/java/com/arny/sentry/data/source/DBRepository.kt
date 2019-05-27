package com.arny.sentry.data.source

import com.arny.sentry.data.models.Asteroid
import io.realm.Realm
import io.realm.internal.IOException

interface DBRepository : BaseRepository {
    fun getDB(): Realm

    fun add(asteroid: Asteroid) {
        val realm = getDB()
        realm.beginTransaction()
        try {
            realm.copyToRealm(asteroid)
            realm.commitTransaction()
        } catch (e: IOException) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
    }

    fun remove(asteroid: Asteroid) {
        val realm = getDB()
        realm.beginTransaction()
        try {
            realm.where(Asteroid::class.java).equalTo("id", asteroid.id).findFirst()?.deleteFromRealm()
            realm.commitTransaction()
        } catch (e: IOException) {
            realm.cancelTransaction()
        } finally {
            realm.close()
        }
    }

}