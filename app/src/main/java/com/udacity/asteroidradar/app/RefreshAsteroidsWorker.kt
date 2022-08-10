package com.udacity.asteroidradar.app

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.repository.AsteroidRepository
import retrofit2.HttpException

class RefreshAsteroidsWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshAsteroidsWorker"
    }

    override suspend fun doWork(): Result {

        val database = AsteroidDataBase.getInstance(applicationContext)
        val repository = AsteroidRepository(database)

        return try {
            repository.refreshAsteroid()
            Result.success()

        } catch (e: HttpException) {
            Result.retry()
        }
    }
}