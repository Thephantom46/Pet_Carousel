package com.example.pet_carousel

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL_DOG = "https://dog.ceo/"
    private const val BASE_URL_CAT = "https://api.thecatapi.com/"

    val retrofitdog: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_DOG)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val retrofitcat: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_CAT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}