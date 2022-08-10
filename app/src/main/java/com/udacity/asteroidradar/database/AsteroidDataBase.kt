package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [AsteroidTable::class], version = 1, exportSchema = false)
abstract class AsteroidDataBase : RoomDatabase() {
    abstract val dao:AsteroidDao
    companion object{
        @Volatile
        private var Instance :AsteroidDataBase? =null
        fun getInstance(context : Context):
                AsteroidDataBase {
            synchronized(this){
                var instance= Instance
                if (instance==null){
                    instance=Room.databaseBuilder(context.applicationContext,
                    AsteroidDataBase::class.java,
                    "Asteroid_db")
                        .fallbackToDestructiveMigration()
                        .build()
                    Instance=instance
                }
                return instance
            }
        }
    }
}