package com.example.meals_app.Retrofit

import com.example.meals_app.Data.Meal
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Retrofit_Helper {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/" // Replace with your base URL

    // Create a Retrofit instance
    val api :Meal_Api by lazy {
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Meal_Api::class.java)
    }


}