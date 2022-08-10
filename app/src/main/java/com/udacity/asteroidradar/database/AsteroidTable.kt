package com.udacity.asteroidradar.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "Asteroid_entity")
data class AsteroidTable(
    @PrimaryKey
    var id :Long,
    @ColumnInfo(name = "codeName")
    var codename: String,
    @ColumnInfo(name = "closeApproachDate")
    var closeApproachDate: String,
    @ColumnInfo(name = "absoluteMagnitude")
    var absoluteMagnitude: Double,
    @ColumnInfo(name = "estimatedDiameter")
    var estimatedDiameter: Double,
    @ColumnInfo(name = "relativeVelocity")
    val relativeVelocity: Double,
    @ColumnInfo(name = "distanceFromEarth")
    val distanceFromEarth: Double,
    @ColumnInfo(name = "isPotentiallyHazardous")
    val isPotentiallyHazardous: Boolean

)