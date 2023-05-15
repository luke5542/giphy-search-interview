package com.example.myapplication42

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val BASE_URL = "https://api.giphy.com/v1/"

    internal val API_KEY = "jxThuav5OGaIQ2gwfzWgHdB9VQURNR0d"

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}