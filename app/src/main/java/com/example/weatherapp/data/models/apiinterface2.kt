package com.example.weatherapp.data.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface apiinterface2 {
    @GET()
    suspend fun getsuggestion(
       @Url url:String
        ) : Response<searchweather>
}