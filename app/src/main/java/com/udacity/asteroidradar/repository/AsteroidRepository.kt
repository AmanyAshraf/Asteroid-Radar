package com.udacity.asteroidradar.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.ApiService
import com.udacity.asteroidradar.api.asAsteroidEntities
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.database.asAsteroids
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class AsteroidRepository (private val dataBase: AsteroidDataBase){

   val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat.getDateInstance() //or use getDateInstance()
    val formatedDate = formatter.format(date)
    val startDate = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault()).format(Date())

    //this how we get the past week date but when i use I get Crash
    /*@RequiresApi(Build.VERSION_CODES.O)
    private val endDate = LocalDateTime.now().minusDays(7)*/

    val endDate ="2022-07-23"

    val asteroidSavedList :LiveData<List<Asteroid>> =Transformations.map(dataBase.dao.getSavedAsteroid()){
        it.asAsteroids()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    val asteroidWeekList :LiveData<List<Asteroid>> =Transformations.map(dataBase.dao.getAsteroidWeek(
        startDate.toString(),
        endDate)){
        it.asAsteroids()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    val asteroidDayList :LiveData<List<Asteroid>> =Transformations.map(dataBase.dao.getAsteroidsDay(
        startDate.toString())){
        it.asAsteroids()
    }
    suspend fun refreshAsteroid(){
        withContext(Dispatchers.IO){
            val asteroids = ApiService.getAsteroids()
            dataBase.dao.insertAsteroid(asteroids.asAsteroidEntities())
        }

    }
    suspend fun getPicture():PictureOfDay{
        lateinit var  pic: PictureOfDay
        withContext(Dispatchers.IO){
            pic =ApiService.getPic()
        }
        return pic
    }

}