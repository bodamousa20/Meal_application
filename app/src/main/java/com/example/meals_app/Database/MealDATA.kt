package com.example.meals_app.Database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class MealDATA (
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String
    )