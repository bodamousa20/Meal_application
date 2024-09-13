package com.example.meals_app.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.meals_app.Data.Meal
import com.example.meals_app.Data.MealDATA


@Dao
interface MealsDAO {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun saveMeal(meal: MealDATA):Long

    @Delete
    suspend fun deleteMeal(meal: MealDATA): Int

    @Query("SELECT * FROM MealDATA")
    fun getAllFavoriteMeals(): LiveData<List<MealDATA>>

    @Query("SELECT * FROM MealDATA WHERE idMeal = :id")
    fun getMealById(id: String): LiveData<MealDATA>
}