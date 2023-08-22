package com.example.weatherapp.data.models

data class Item(
    val address: Address,
    val administrativeAreaType: String,
    val highlights: Highlights,
    val id: String,
    val language: String,
    val localityType: String,
    val resultType: String,
    val title: String
)