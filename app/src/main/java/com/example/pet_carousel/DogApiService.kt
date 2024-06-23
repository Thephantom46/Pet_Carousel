package com.example.pet_carousel

import retrofit2.Call
import retrofit2.http.GET

interface DogApiService {
    @GET("api/breeds/image/random")
    fun getRandomDogImage(): Call<DogResponse>
}
