package com.udacity.asteroidradar.database

import com.udacity.asteroidradar.Asteroid

fun List<AsteroidTable>.asAsteroids() : List<Asteroid>{
    return map {
        Asteroid(
            id=it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }

}