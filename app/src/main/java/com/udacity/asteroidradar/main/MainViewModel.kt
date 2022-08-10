package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.database.AsteroidDataBase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch

class MainViewModel(app:Application) : AndroidViewModel(app) {
    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>()
    val navigateToDetailFragment
        get() = _navigateToDetailFragment

    private val database =AsteroidDataBase.getInstance(app)
    private val repository =AsteroidRepository(database)

    private val filter = MutableLiveData(AsteroidFilter.ALL)
    @RequiresApi(Build.VERSION_CODES.O)
    val asteroids =
        Transformations.switchMap(filter){
        when (it){
            AsteroidFilter.WEEK -> repository.asteroidWeekList
            AsteroidFilter.TODAY -> repository.asteroidDayList
            else -> repository.asteroidSavedList
        }
    }
    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    init {
        refresh()
        pic()
    }
    fun onChangeFilter(_filter: AsteroidFilter) {
        filter.postValue(_filter)
    }
    fun onAsteroidItemClick(data: Asteroid) {
        _navigateToDetailFragment.value = data
    }

    fun onDetailFragmentNavigated() {
        _navigateToDetailFragment.value = null
    }
    private fun refresh(){
        viewModelScope.launch {
            try {
                repository.refreshAsteroid()
            }catch (e : Exception){
                e.printStackTrace()
            }
        }
    }
    private fun pic(){
        viewModelScope.launch {
            try {
                _pictureOfDay.value =repository.getPicture()
            }catch (e : Exception){

            }
        }
    }



}