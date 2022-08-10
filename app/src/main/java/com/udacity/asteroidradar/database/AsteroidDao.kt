package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAsteroid(asteroids: List<AsteroidTable>)

    @Query("SELECT * FROM Asteroid_entity ORDER BY closeApproachDate DESC")
    fun getSavedAsteroid():LiveData<List<AsteroidTable>>

    @Query("SELECT * FROM Asteroid_entity WHERE closeApproachDate BETWEEN :afterWeekDate AND :todayDate  ORDER BY closeApproachDate DESC")
    fun getAsteroidWeek(todayDate : String, afterWeekDate : String):LiveData<List<AsteroidTable>>

    @Query("SELECT * FROM Asteroid_entity WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsDay(startDate: String): LiveData<List<AsteroidTable>>
}