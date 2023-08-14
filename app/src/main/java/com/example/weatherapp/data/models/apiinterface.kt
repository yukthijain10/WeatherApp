package com.example.weatherapp.data.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface apiinterface {
    @GET("weather?")
    suspend fun getCurrentWeather(
        @Query("q") city : String,
        @Query("units") units : String,
        @Query("appid") apiKey : String,

        ) : Response<CurrentWeather>
}