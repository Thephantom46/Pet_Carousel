package com.example.pet_carousel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

interface CatApiService {
    @Headers("x-api-key: live_5mUbOKXy2x0an7DxxByR7lAJRzTHdCKHYvy29peHjEJVj6zQOozVD8DmAcoBm5OX")
    @GET("v1/images/search")
    fun getRandomCatImage(): Call<List<CatImage>>
}