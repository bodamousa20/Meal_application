package com.example.meals_app.Retrofit

import com.example.meals_app.Data.CategoryList
import com.example.meals_app.Data.Countary_list
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealList
import com.example.meals_app.Data.categoryMeal
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Meal_Api {
    @GET("random.php")
    suspend fun getRandomMeal(): MealList

    @GET("categories.php")
    suspend fun getAllCategory():CategoryList

    @GET("list.php?a=list")
    suspend fun getAllCountary() :Countary_list

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName:String):Call<categoryMeal>

    @GET("lookup.php")
     fun getMealByID(@Query("i") mealid:String): Call<MealList>



    @GET("filter.php")
    fun getMealsByCountary(@Query("a") CountaryName: String) : Call<categoryMeal>

  @GET("search.php")
    fun searchMeal(@Query("s") mealName:String):Call<MealList>
}