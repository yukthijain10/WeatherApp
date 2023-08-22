package com.example.weatherapp.data.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Retrofitinstance2 {
    val api2 :apiinterface2 by lazy {
        Retrofit.Builder()
            .baseUrl("https://autocomplete.search.hereapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(apiinterface2::class.java)
    }
}