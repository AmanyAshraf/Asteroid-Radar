package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()


private val retrofit = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(Constants.BASE_URL)
                .build()


interface AsteroidService {
@GET("neo/rest/v1/feed")
suspend fun getAsteroids(
    @Query("start_date") startDate: String,
    @Query("end_date") endDate: String,
    @Query("api_key") apiKey: String): String

@GET("planetary/apod")
suspend fun getPic(
    @Query("api_key") apiKey: String ) : PictureOfDay

}
object ApiService{
    val retrofitService:AsteroidService by lazy {
        retrofit.create(AsteroidService ::class.java)
    }
    suspend fun getAsteroids():List<Asteroid>{
        val response= retrofitService.getAsteroids("","",Constants.API_KEY)
        val jsonObj =JSONObject(response)
        return parseAsteroidsJsonResult(jsonObj)
    }
    suspend fun getPic()= retrofitService.getPic(Constants.API_KEY)
}